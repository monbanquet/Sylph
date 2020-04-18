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

import fr.monbanquet.sylph.delegate.HttpClientDelegate;
import fr.monbanquet.sylph.exception.SylphHttpRequestException;
import fr.monbanquet.sylph.logger.RequestLogger;
import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.ResponseProcessor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SylphHttpClient extends HttpClientDelegate {

    private SylphHttpRequestBuilder baseRequestBuilder;
    private Parser parser;
    private RequestLogger requestLogger;
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

    // ---  --- //

    public SylphHttpClient GET(String uri) {
        return GET(URI.create(uri));
    }

    public SylphHttpClient GET(URI uri) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .GET());
    }

    public SylphHttpClient POST(String uri) {
        return POST(URI.create(uri));
    }

    public SylphHttpClient POST(URI uri) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.noBody()));
    }

    public <T> SylphHttpClient POST(String uri, T body) {
        return POST(URI.create(uri), body);
    }

    public <T> SylphHttpClient POST(URI uri, T body) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(parser.serialize(body))));
    }

    public SylphHttpClient POST(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .POST(bodyPublisher));
    }

    public <T> SylphHttpClient POST(T body) {
        return copy(baseRequestBuilder.copy()
                .POST(HttpRequest.BodyPublishers.ofString(parser.serialize(body))));
    }

    public SylphHttpClient POST(HttpRequest.BodyPublisher bodyPublisher) {
        return copy(baseRequestBuilder.copy()
                .POST(bodyPublisher));
    }

    public <T> SylphHttpClient PUT(String uri) {
        return PUT(URI.create(uri));
    }

    public <T> SylphHttpClient PUT(URI uri) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.noBody()));
    }

    public <T> SylphHttpClient PUT(String uri, T body) {
        return PUT(URI.create(uri), body);
    }

    public <T> SylphHttpClient PUT(URI uri, T body) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(parser.serialize(body))));
    }

    public SylphHttpClient PUT(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .PUT(bodyPublisher));
    }

    public <T> SylphHttpClient PUT(T body) {
        return copy(baseRequestBuilder.copy()
                .PUT(HttpRequest.BodyPublishers.ofString(parser.serialize(body))));
    }

    public SylphHttpClient PUT(HttpRequest.BodyPublisher bodyPublisher) {
        return copy(baseRequestBuilder.copy()
                .PUT(bodyPublisher));
    }

    public SylphHttpClient DELETE(String uri) {
        return DELETE(URI.create(uri));
    }

    public SylphHttpClient DELETE(URI uri) {
        return copy(baseRequestBuilder.copy()
                .uri(uri)
                .DELETE());
    }

    // ---  --- //

    @Override
    public <T> SylphHttpResponse<T, T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return new SylphHttpResponseSimple<>(doSend(request, responseBodyHandler));
    }

    public <T> SylphHttpResponse<T, T> send(HttpResponse.BodyHandler<T> responseBodyHandler) {
        return new SylphHttpResponseSimple<>(doSend(getRequest(), responseBodyHandler));
    }

    public <T> SylphHttpResponse<String, T> send(HttpRequest request, Class<T> returnType) {
        return new SylphHttpResponseWithTransform<>(doSend(request, HttpResponse.BodyHandlers.ofString()), returnType, parser);
    }

    public <T> SylphHttpResponse<String, T> send(Class<T> returnType) {
        return new SylphHttpResponseWithTransform<>(doSend(getRequest(), HttpResponse.BodyHandlers.ofString()), returnType, parser);
    }

    public SylphHttpResponse<Void, Void> send() {
        return new SylphHttpResponseSimple(doSend(getRequest(), HttpResponse.BodyHandlers.discarding()));
    }

    private <T> HttpResponse<T> doSend(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        requestLogger.log(request);
        HttpResponse<T> response;
        try {
            response = httpClient.send(request, responseBodyHandler);
        } catch (Exception e) {
            String requestUri = request.uri().toString();
            String requestMethod = request.method();
            throw new SylphHttpRequestException(requestUri, requestMethod, e);
        }
        responseLogger.log(response);
        responseProcessor.processResponse(response);
        return response;
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return httpClient.sendAsync(request, responseBodyHandler)
                .thenApply(SylphHttpResponseSimple::new);
    }

    public <T> CompletableFuture<SylphHttpResponse<T, T>> sendAsync(HttpResponse.BodyHandler<T> responseBodyHandler) {
        return doSendAsync(getRequest(), responseBodyHandler)
                .thenApply(SylphHttpResponseSimple::new);
    }

    public <T> CompletableFuture<SylphHttpResponse<String, T>> sendAsync(HttpRequest request, Class<T> returnType) {
        return doSendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> new SylphHttpResponseWithTransform<>(response, returnType, parser));
    }

    public <T> CompletableFuture<SylphHttpResponse<String, T>> sendAsync(Class<T> returnType) {
        return doSendAsync(getRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> new SylphHttpResponseWithTransform<>(response, returnType, parser));
    }

    public CompletableFuture<SylphHttpResponse<Void, Void>> sendAsync() {
        HttpRequest request = getRequest();
        CompletableFuture<HttpResponse<Void>> httpResponseCompletableFuture = doSendAsync(request, HttpResponse.BodyHandlers.discarding());
        if (httpResponseCompletableFuture.isCompletedExceptionally()) {
             httpResponseCompletableFuture.join();
        }
        return httpResponseCompletableFuture
                .thenApply(SylphHttpResponseSimple::new);
    }


    private <T> CompletableFuture<HttpResponse<T>> doSendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
        return CompletableFuture
                .runAsync(() -> requestLogger.log(request))
                .thenCompose(r -> httpClient.sendAsync(request, responseBodyHandler))
                .exceptionally(throwable -> {
                    String requestUri = request.uri().toString();
                    String requestMethod = request.method();
                    throw new SylphHttpRequestException(requestUri, requestMethod, throwable);
                })
                .thenApply(response -> {
                    responseLogger.log(response);
                    return response;
                })
                .thenApply(response -> {
                    responseProcessor.processResponse(response);
                    return response;
                });
    }

    // ---  --- //

    public <T> T body(Class<T> returnType) {
        return send(returnType).asObject();
    }

    public <T> List<T> bodyList(Class<T> returnType) {
        return send(returnType).asList();
    }

    public <T> CompletableFuture<T> bodyAsync(Class<T> returnType) {
        return sendAsync(returnType).thenApply(SylphHttpResponse::asObject);
    }

    public <T> CompletableFuture<List<T>> bodyListAsync(Class<T> returnType) {
        return sendAsync(returnType).thenApply(SylphHttpResponse::asList);
    }

    public CompletableFuture<Void> bodyAsync() {
        return sendAsync().thenAccept(r -> {
        });
    }

    // ---  --- //

    public SylphHttpClient copy() {
        return copy(baseRequestBuilder.copy());
    }

    public SylphHttpClient copy(SylphHttpRequestBuilder requestBuilder) {
        return Sylph.builder()
                .setBaseRequest(requestBuilder)
                .setRequestLogger(requestLogger)
                .setResponseLogger(responseLogger)
                .setResponseProcessor(responseProcessor)
                .setClient(internalCopy())
                .getClient();
    }

    protected SylphHttpClientBuilder internalCopy() {
        SylphHttpClientBuilder builder = SylphHttpClient.newBuilder();
        cookieHandler().ifPresent(value -> builder.cookieHandler(value));
        connectTimeout().ifPresent(value -> builder.connectTimeout(value));
        builder.sslContext(sslContext());
        builder.sslParameters(sslParameters());
        executor().ifPresent(value -> builder.executor(value));
        builder.followRedirects(followRedirects());
        builder.version(version());
        proxy().ifPresent(value -> builder.proxy(value));
        authenticator().ifPresent(value -> builder.authenticator(value));
        return builder;
    }

    // ---  --- //

    private HttpRequest getRequest() {
        return baseRequestBuilder.build();
    }

    public void setBaseRequestBuilder(SylphHttpRequestBuilder baseRequestBuilder) {
        this.baseRequestBuilder = baseRequestBuilder;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setRequestLogger(RequestLogger requestLogger) {
        this.requestLogger = requestLogger;
    }

    public void setResponseLogger(ResponseLogger responseLogger) {
        this.responseLogger = responseLogger;
    }

    public void setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

}