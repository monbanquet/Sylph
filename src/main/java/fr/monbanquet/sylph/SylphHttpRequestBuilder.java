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
package fr.monbanquet.sylph;

import fr.monbanquet.sylph.parser.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Objects;

public class SylphHttpRequestBuilder implements HttpRequest.Builder {

    protected final HttpRequest.Builder internalBuilder;
    protected Parser parser;

    SylphHttpRequestBuilder(HttpRequest.Builder internalBuilder) {
        this.internalBuilder = internalBuilder;
    }

    public static SylphHttpRequestBuilder newBuilder(URI uri) {
        return new SylphHttpRequestBuilder(
                HttpRequest.newBuilder(uri)
                        .header("Content-Type", "application/json; charset=utf-8")
                        .timeout(Duration.ofSeconds(30)));
    }

    public static SylphHttpRequestBuilder newBuilder(String uri) {
        return new SylphHttpRequestBuilder(
                newBuilder(URI.create(uri))
                        .header("Content-Type", "application/json; charset=utf-8")
                        .timeout(Duration.ofSeconds(30)));
    }

    public static SylphHttpRequestBuilder newBuilder() {
        return new SylphHttpRequestBuilder(
                HttpRequest.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .timeout(Duration.ofSeconds(30)));
    }


    // ---  --- //

    public SylphHttpRequestBuilder uri(String uri) {
        return uri(URI.create(uri));
    }

    public SylphHttpRequestBuilder POST(String body) {
        return POST(HttpRequest.BodyPublishers.ofString(body));
    }

    public <T> SylphHttpRequestBuilder POST(T body) {
        Objects.requireNonNull(parser, "Request require a Parser");
        return POST(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
    }

    public SylphHttpRequestBuilder PUT(String body) {
        return PUT(HttpRequest.BodyPublishers.ofString(body));
    }

    public <T> SylphHttpRequestBuilder PUT(T body) {
        Objects.requireNonNull(parser, "Request require a Parser");
        return PUT(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
    }

    // ---  --- //

    @Override
    public SylphHttpRequest build() {
        return new SylphHttpRequest(internalBuilder.build());
    }

    SylphHttpRequestBuilder parser(Parser parser) {
        this.parser = parser;
        return this;
    }

    // --- Delegate HttpRequest.Builder --- //

    @Override
    public SylphHttpRequestBuilder uri(URI uri) {
        this.internalBuilder.uri(uri);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder expectContinue(boolean enable) {
        this.internalBuilder.expectContinue(enable);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder version(HttpClient.Version version) {
        this.internalBuilder.version(version);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder header(String name, String value) {
        this.internalBuilder.header(name, value);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder headers(String... headers) {
        this.internalBuilder.headers(headers);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder timeout(Duration duration) {
        this.internalBuilder.timeout(duration);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder setHeader(String name, String value) {
        this.internalBuilder.setHeader(name, value);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder GET() {
        this.internalBuilder.GET();
        return this;
    }

    @Override
    public SylphHttpRequestBuilder POST(HttpRequest.BodyPublisher bodyPublisher) {
        this.internalBuilder.POST(bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder PUT(HttpRequest.BodyPublisher bodyPublisher) {
        this.internalBuilder.PUT(bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder DELETE() {
        this.internalBuilder.DELETE();
        return this;
    }

    @Override
    public SylphHttpRequestBuilder method(String method, HttpRequest.BodyPublisher bodyPublisher) {
        this.internalBuilder.method(method, bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder copy() {
        this.internalBuilder.copy();
        return this;
    }

}
