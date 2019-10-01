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

    private static final Logger logger = LoggerFactory.getLogger(DefaultResponseLogger.class);

    private final SylphLogger level;

    DefaultResponseLogger() {
        this.level = SylphLogger.DEBUG;
    }

    DefaultResponseLogger(SylphLogger level) {
        this.level = level;
    }

    public static ResponseLogger create() {
        return new DefaultResponseLogger();
    }

    public static ResponseLogger create(SylphLogger level) {
        return new DefaultResponseLogger(level);
    }

    public <T> HttpResponse<T> log(HttpResponse<T> response) {
        if (level.isEnabled(logger)) {
            String responseHeader = response.headers().map().entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.joining(", "));
            if (!responseHeader.isBlank()) {
                level.prepare(logger).log(" - Response Headers : {}", responseHeader);
            }
            level.prepare(logger).log(" - Response Status Code: {}", response.statusCode());
            level.prepare(logger).log(" - Response Body : {}", response.body());
        }
        return response;
    }

}
