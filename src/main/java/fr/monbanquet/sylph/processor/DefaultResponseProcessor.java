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
package fr.monbanquet.sylph.processor;

import fr.monbanquet.sylph.exception.SylphHttpResponseException;
import fr.monbanquet.sylph.logger.DefaultResponseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

public class DefaultResponseProcessor implements ResponseProcessor {

    private static Logger log = LoggerFactory.getLogger(DefaultResponseLogger.class);

    public static ResponseProcessor create() {
        return new DefaultResponseProcessor();
    }

    @Override
    public <T> HttpResponse<T> processResponse(HttpResponse<T> response) throws SylphHttpResponseException {
        if (response.statusCode() < 300) {
            return response;

        } else {
            String requestUri = response.request().uri().toString();
            String requestMethod = response.request().method();
            int responseCode = response.statusCode();
            String errorBody = response.body() instanceof String ? (String) response.body() : "";
            log.error("Error while executing call {};{} : responseCode={}, errorBody={}",
                    requestMethod, requestUri, responseCode, errorBody);

            throw new SylphHttpResponseException(requestUri, requestMethod, responseCode, errorBody);
        }
    }

}
