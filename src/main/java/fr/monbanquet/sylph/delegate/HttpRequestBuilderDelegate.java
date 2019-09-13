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
import java.net.http.HttpRequest;
import java.time.Duration;

public class HttpRequestBuilderDelegate implements HttpRequest.Builder {

    protected final HttpRequest.Builder builder;

    public HttpRequestBuilderDelegate(HttpRequest.Builder builder) {
        this.builder = builder;
    }

    @Override
    public HttpRequest.Builder uri(URI uri) {
        return builder.uri(uri);
    }

    @Override
    public HttpRequest.Builder expectContinue(boolean enable) {
        return builder.expectContinue(enable);
    }

    @Override
    public HttpRequest.Builder version(HttpClient.Version version) {
        return builder.version(version);
    }

    @Override
    public HttpRequest.Builder header(String name, String value) {
        return builder.header(name, value);
    }

    @Override
    public HttpRequest.Builder headers(String... headers) {
        return builder.headers(headers);
    }

    @Override
    public HttpRequest.Builder timeout(Duration duration) {
        return builder.timeout(duration);
    }

    @Override
    public HttpRequest.Builder setHeader(String name, String value) {
        return builder.setHeader(name, value);
    }

    @Override
    public HttpRequest.Builder GET() {
        return builder.GET();
    }

    @Override
    public HttpRequest.Builder POST(HttpRequest.BodyPublisher bodyPublisher) {
        return builder.POST(bodyPublisher);
    }

    @Override
    public HttpRequest.Builder PUT(HttpRequest.BodyPublisher bodyPublisher) {
        return builder.PUT(bodyPublisher);
    }

    @Override
    public HttpRequest.Builder DELETE() {
        return builder.DELETE();
    }

    @Override
    public HttpRequest.Builder method(String method, HttpRequest.BodyPublisher bodyPublisher) {
        return builder.method(method, bodyPublisher);
    }

    @Override
    public HttpRequest build() {
        return builder.build();
    }

    @Override
    public HttpRequest.Builder copy() {
        return builder.copy();
    }
}
