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

import fr.monbanquet.sylph.delegate.HttpRequestBuilderDelegate;
import fr.monbanquet.sylph.parser.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

public class SylphHttpRequestBuilder extends HttpRequestBuilderDelegate {

    protected Parser parser;

    protected static final String HEADER_CONTENT_TYPE = "Content-Type";
    protected static final String HEADER_CONTENT_TYPE_VALUE = "application/json; charset=utf-8";
    protected boolean hasContentType = false;
    protected boolean hasTimeout = false;


    SylphHttpRequestBuilder(HttpRequest.Builder internalBuilder) {
        super(internalBuilder);
    }

    public static SylphHttpRequestBuilder newBuilder(URI uri) {
        return new SylphHttpRequestBuilder(HttpRequest.newBuilder(uri));
    }

    public static SylphHttpRequestBuilder newBuilder(String uri) {
        return new SylphHttpRequestBuilder(newBuilder(URI.create(uri)));
    }

    public static SylphHttpRequestBuilder newBuilder() {
        return new SylphHttpRequestBuilder(HttpRequest.newBuilder());
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
        if (!hasContentType) {
            builder.header(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE);
        }
        if (!hasTimeout) {
            builder.timeout(Duration.ofSeconds(30));
        }
        return new SylphHttpRequest(builder.build());
    }

    SylphHttpRequestBuilder parser(Parser parser) {
        this.parser = parser;
        return this;
    }

    // ---  --- //

    @Override
    public SylphHttpRequestBuilder uri(URI uri) {
        this.builder.uri(uri);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder expectContinue(boolean enable) {
        this.builder.expectContinue(enable);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder version(HttpClient.Version version) {
        this.builder.version(version);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder header(String name, String value) {
        this.builder.header(name, value);
        if (HEADER_CONTENT_TYPE.equals(name)) {
            this.hasContentType = true;
        }
        return this;
    }

    @Override
    public SylphHttpRequestBuilder headers(String... headers) {
        this.builder.headers(headers);
        Arrays.stream(headers).forEach(header -> {
            if (Objects.nonNull(header) && header.contains(HEADER_CONTENT_TYPE)) {
                this.hasContentType = true;
            }
        });
        return this;
    }

    @Override
    public SylphHttpRequestBuilder timeout(Duration duration) {
        this.builder.timeout(duration);
        this.hasTimeout = true;
        return this;
    }

    @Override
    public SylphHttpRequestBuilder setHeader(String name, String value) {
        this.builder.setHeader(name, value);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder GET() {
        this.builder.GET();
        return this;
    }

    @Override
    public SylphHttpRequestBuilder POST(HttpRequest.BodyPublisher bodyPublisher) {
        this.builder.POST(bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder PUT(HttpRequest.BodyPublisher bodyPublisher) {
        this.builder.PUT(bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder DELETE() {
        this.builder.DELETE();
        return this;
    }

    @Override
    public SylphHttpRequestBuilder method(String method, HttpRequest.BodyPublisher bodyPublisher) {
        this.builder.method(method, bodyPublisher);
        return this;
    }

    @Override
    public SylphHttpRequestBuilder copy() {
        this.builder.copy();
        return this;
    }

}
