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

import java.net.http.HttpRequest;
import java.util.stream.Collectors;

public class DefaultRequestLogger implements RequestLogger {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRequestLogger.class);

    private final SylphLogger level;

    DefaultRequestLogger() {
        this.level = SylphLogger.DEBUG;
    }

    DefaultRequestLogger(SylphLogger level) {
        this.level = level;
    }

    public static RequestLogger create() {
        return new DefaultRequestLogger();
    }

    public static RequestLogger create(SylphLogger level) {
        return new DefaultRequestLogger(level);
    }

    @Override
    public HttpRequest log(HttpRequest request) {
        if (level.isEnabled(logger)) {
            String requestHeaders = request.headers().map().entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.joining(", "));
            if (!requestHeaders.isBlank()) {
                level.prepare(logger).log(" - Request Headers : {}", requestHeaders);
            }
            level.prepare(logger).log(" - Request {} {}", request.method(), request.uri());
        }
        return request;
    }
}
