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
package fr.monbanquet.sylph.delegate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

public class HttpRequestDelegate extends HttpRequest {

    protected HttpRequest request;

    public static Builder newBuilder(URI uri) {
        return HttpRequest.newBuilder(uri);
    }

    public static Builder newBuilder() {
        return HttpRequest.newBuilder();
    }

    @Override
    public Optional<BodyPublisher> bodyPublisher() {
        return request.bodyPublisher();
    }

    @Override
    public String method() {
        return request.method();
    }

    @Override
    public Optional<Duration> timeout() {
        return request.timeout();
    }

    @Override
    public boolean expectContinue() {
        return request.expectContinue();
    }

    @Override
    public URI uri() {
        return request.uri();
    }

    @Override
    public Optional<HttpClient.Version> version() {
        return request.version();
    }

    @Override
    public HttpHeaders headers() {
        return request.headers();
    }
}
