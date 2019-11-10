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

import fr.monbanquet.sylph.delegate.HttpClientBuilderDelegate;
import fr.monbanquet.sylph.logger.RequestLogger;
import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.ResponseProcessor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executor;

public class SylphHttpClientBuilder extends HttpClientBuilderDelegate {

    private SylphHttpRequestBuilder baseRequestBuilder;
    private Parser parser;
    private RequestLogger requestLogger;
    private ResponseLogger responseLogger;
    private ResponseProcessor responseProcessor;

    SylphHttpClientBuilder() {

    }

    @Override
    public SylphHttpClient build() {
        Objects.requireNonNull(baseRequestBuilder, "Client require a BaseRequestBuilder");
        Objects.requireNonNull(parser, "Client require a Parser");
        Objects.requireNonNull(requestLogger, "Client require a RequestLogger");
        Objects.requireNonNull(responseLogger, "Client require a ResponseLogger");
        Objects.requireNonNull(responseProcessor, "Client require a ResponseProcessor");
        HttpClient client = builder.build();
        SylphHttpClient sylphClient = new SylphHttpClient();
        sylphClient.setBaseRequestBuilder(baseRequestBuilder);
        sylphClient.setParser(parser);
        sylphClient.setRequestLogger(requestLogger);
        sylphClient.setResponseLogger(responseLogger);
        sylphClient.setResponseProcessor(responseProcessor);
        sylphClient.setHttpClient(client);
        return sylphClient;
    }

    public SylphHttpClientBuilder authBase64(String username, String password) {
        Objects.requireNonNull(username, "Cannot do basic authentification request with empty username");
        Objects.requireNonNull(password, "Cannot do basic authentification request with empty password");
        this.authenticator(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });
        return this;
    }

    void baseRequestBuilder(SylphHttpRequestBuilder baseRequestBuilder) {
        this.baseRequestBuilder = baseRequestBuilder;
    }

    void parser(Parser parser) {
        this.parser = parser;
    }

    void requestLogger(RequestLogger requestLogger) {
        this.requestLogger = requestLogger;
    }

    void responseLogger(ResponseLogger responseLogger) {
        this.responseLogger = responseLogger;
    }

    void responseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    // ---  --- //

    @Override
    public SylphHttpClientBuilder cookieHandler(CookieHandler cookieHandler) {
        builder.cookieHandler(cookieHandler);
        return this;
    }

    @Override
    public SylphHttpClientBuilder connectTimeout(Duration duration) {
        builder.connectTimeout(duration);
        return this;
    }

    @Override
    public SylphHttpClientBuilder sslContext(SSLContext sslContext) {
        builder.sslContext(sslContext);
        return this;
    }

    @Override
    public SylphHttpClientBuilder sslParameters(SSLParameters sslParameters) {
        builder.sslParameters(sslParameters);
        return this;
    }

    @Override
    public SylphHttpClientBuilder executor(Executor executor) {
        builder.executor(executor);
        return this;
    }

    @Override
    public SylphHttpClientBuilder followRedirects(HttpClient.Redirect policy) {
        builder.followRedirects(policy);
        return this;
    }

    @Override
    public SylphHttpClientBuilder version(HttpClient.Version version) {
        builder.version(version);
        return this;
    }

    @Override
    public SylphHttpClientBuilder priority(int priority) {
        builder.priority(priority);
        return this;
    }

    @Override
    public SylphHttpClientBuilder proxy(ProxySelector proxySelector) {
        builder.proxy(proxySelector);
        return this;
    }

    @Override
    public SylphHttpClientBuilder authenticator(Authenticator authenticator) {
        builder.authenticator(authenticator);
        return this;
    }

}
