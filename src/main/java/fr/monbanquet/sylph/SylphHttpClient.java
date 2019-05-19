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

import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.ResponseProcessor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SylphHttpClient extends HttpClient {

    private SylphHttpRequestBuilder baseRequest;
    private HttpClient httpClient;
    private Parser parser;
    private ResponseLogger responseLogger;
    private ResponseProcessor responseProcessor;

    SylphHttpClient() {

    }

    public static SylphHttpClient newHttpClient() {
        return Sylph.newClient();
    }

    public static SylphHttpClientBuilder newBuilder() {
        return new SylphHttpClientBuilder();
    }

    public <T> SylphHttpClient GET(String uri) {
        return GET(URI.create(uri));
    }

    public <T> SylphHttpClient GET(URI uri) {
        this.baseRequest = baseRequest.copy()
                .uri(uri)
                .GET();
        return this;
    }

    public <T> SylphHttpClient POST(String uri) {
        return POST(URI.create(uri));
    }

    public <T> SylphHttpClient POST(URI uri) {
        this.baseRequest = baseRequest.copy()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.noBody());
        return this;
    }

    public <T> SylphHttpClient POST(String uri, T body) {
        return POST(URI.create(uri), body);
    }

    public <T> SylphHttpClient POST(URI uri, T body) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
        return this;
    }

    public SylphHttpClient POST(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .POST(bodyPublisher);
        return this;
    }

    public <T> SylphHttpClient POST(T body) {
        baseRequest = baseRequest.copy()
                .POST(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
        return this;
    }

    public SylphHttpClient POST(HttpRequest.BodyPublisher bodyPublisher) {
        baseRequest = baseRequest.copy()
                .POST(bodyPublisher);
        return this;
    }

    public <T> SylphHttpClient PUT(String uri) {
        return PUT(URI.create(uri));
    }

    public <T> SylphHttpClient PUT(URI uri) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.noBody());
        return this;
    }

    public <T> SylphHttpClient PUT(String uri, T body) {
        return PUT(URI.create(uri), body);
    }

    public <T> SylphHttpClient PUT(URI uri, T body) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
        return this;
    }

    public SylphHttpClient PUT(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .PUT(bodyPublisher);
        return this;
    }

    public <T> SylphHttpClient PUT(T body) {
        baseRequest = baseRequest.copy()
                .PUT(HttpRequest.BodyPublishers.ofString(parser.serialize(body)));
        return this;
    }

    public SylphHttpClient PUT(HttpRequest.BodyPublisher bodyPublisher) {
        baseRequest = baseRequest.copy()
                .PUT(bodyPublisher);
        return this;
    }

    public SylphHttpClient DELETE(String uri) {
        return DELETE(URI.create(uri));
    }

    public SylphHttpClient DELETE(URI uri) {
        baseRequest = baseRequest.copy()
                .uri(uri)
                .DELETE();
        return this;
    }

    // ---  --- //

    @Override
    public <T> SylphHttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return new SylphHttpResponse<>(doSend(request, responseBodyHandler));
    }

    public <T> SylphHttpResponse<T> send(HttpResponse.BodyHandler<T> responseBodyHandler) {
        return new SylphHttpResponse<>(doSend(getRequest(), responseBodyHandler));
    }

    public <T> SylphHttpResponse<T> send(Class<T> returnType) {
        return new SylphHttpResponse<>(doSend(getRequest(), SylphHttpResponse.BodyHandlers.ofObject(returnType, parser)));
    }

    public <T> HttpResponse<List<T>> sendList(Class<T> returnType) {
        return new SylphHttpResponse<>(doSend(getRequest(), SylphHttpResponse.BodyHandlers.ofList(returnType, parser)));
    }

    public SylphHttpResponse<Void> send() {
        return new SylphHttpResponse(doSend(getRequest(), HttpResponse.BodyHandlers.discarding()));
    }

    private <T> HttpResponse<T> doSend(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpResponse<T> response;
        try {
            response = httpClient.send(request, responseBodyHandler);
        } catch (Exception e) {
            String requestUri = request.uri().toString();
            String requestMethod = request.method();
            throw new SylphHttpRequestException(requestUri, requestMethod, e);
        }
        return Optional.of(response)
                .map(responseLogger::log)
                .map(responseProcessor::processResponse)
                .get();
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return httpClient.sendAsync(request, responseBodyHandler)
                .thenApply(SylphHttpResponse::new);
    }

    public <T> CompletableFuture<SylphHttpResponse<T>> sendAsync(HttpResponse.BodyHandler<T> responseBodyHandler) {
        return doSendAsync(getRequest(), responseBodyHandler);
    }

    public <T> CompletableFuture<SylphHttpResponse<T>> sendAsync(Class<T> returnType) {
        return doSendAsync(getRequest(), SylphHttpResponse.BodyHandlers.ofObject(returnType, parser));
    }

    public <T> CompletableFuture<SylphHttpResponse<List<T>>> sendListAsync(Class<T> returnType) {
        return doSendAsync(getRequest(), SylphHttpResponse.BodyHandlers.ofList(returnType, parser));
    }

    public CompletableFuture<SylphHttpResponse<Void>> sendAsync() {
        return doSendAsync(getRequest(), HttpResponse.BodyHandlers.discarding());
    }

    private <T> CompletableFuture<SylphHttpResponse<T>> doSendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return httpClient.sendAsync(request, responseBodyHandler)
                .thenApply(response -> responseLogger.log(response))
                .thenApply(response -> responseProcessor.processResponse(response))
                .thenApply(SylphHttpResponse::new);
    }

    // ---  --- //

    public <T> T body(Class<T> returnType) {
        return send(returnType).body();
    }

    public <T> List<T> bodyList(Class<T> returnType) {
        return sendList(returnType).body();
    }

    public <T> CompletableFuture<T> bodyAsync(Class<T> returnType) {
        return sendAsync(returnType).thenApply(SylphHttpResponse::body);
    }

    public <T> CompletableFuture<List<T>> bodyListAsync(Class<T> returnType) {
        return sendListAsync(returnType).thenApply(SylphHttpResponse::body);
    }

    public CompletableFuture<Void> bodyAsync() {
        return sendAsync().thenAccept(r -> {});
    }

    // ---  --- //

    private HttpRequest getRequest() {
        return baseRequest.build();
    }

    public void setBaseRequest(SylphHttpRequestBuilder baseRequest) {
        this.baseRequest = baseRequest;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setResponseLogger(ResponseLogger responseLogger) {
        this.responseLogger = responseLogger;
    }

    public void setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    // --- Delegate HttpClient --- //

    @Override
    public Optional<CookieHandler> cookieHandler() {
        return httpClient.cookieHandler();
    }

    @Override
    public Optional<Duration> connectTimeout() {
        return httpClient.connectTimeout();
    }

    @Override
    public Redirect followRedirects() {
        return httpClient.followRedirects();
    }

    @Override
    public Optional<ProxySelector> proxy() {
        return httpClient.proxy();
    }

    @Override
    public SSLContext sslContext() {
        return httpClient.sslContext();
    }

    @Override
    public SSLParameters sslParameters() {
        return httpClient.sslParameters();
    }

    @Override
    public Optional<Authenticator> authenticator() {
        return httpClient.authenticator();
    }

    @Override
    public Version version() {
        return httpClient.version();
    }

    @Override
    public Optional<Executor> executor() {
        return httpClient.executor();
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
        return httpClient.sendAsync(request, responseBodyHandler, pushPromiseHandler);
    }

    @Override
    public WebSocket.Builder newWebSocketBuilder() {
        return httpClient.newWebSocketBuilder();
    }
}
