/*
 * MIT License
 *
 * Copyright (c) 2019 Monbanquet.fr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fr.monbanquet.sylph.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public class DefaultResponseLogger implements ResponseLogger {

    private static Logger log = LoggerFactory.getLogger(DefaultResponseLogger.class);

    public static ResponseLogger create() {
        return new DefaultResponseLogger();
    }

    public <T> HttpResponse<T> log(HttpResponse<T> response) {
        if (log.isDebugEnabled()) {
            String requestHeaders = response.request().headers().map().entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.joining(", "));
            log.debug(" - Request Headers : {}", requestHeaders);
            log.debug(" - Request Method : {}", response.request().method());
            log.debug(" - Request URI : {}", response.request().uri());

            String responseHeader = response.headers().map().entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.joining(", "));
            log.debug(" - Response Headers : {}", responseHeader);
            log.debug(" - Response Status Code: {}", response.statusCode());
            log.debug(" - Response Body : {}", response.body());
        }
        return response;
    }

}
