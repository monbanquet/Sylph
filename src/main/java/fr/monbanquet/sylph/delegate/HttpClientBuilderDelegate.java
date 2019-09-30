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

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.Executor;

public class HttpClientBuilderDelegate implements HttpClient.Builder  {

    protected HttpClient.Builder builder = HttpClient.newBuilder();

    @Override
    public HttpClient.Builder cookieHandler(CookieHandler cookieHandler) {
        return builder.cookieHandler(cookieHandler);
    }

    @Override
    public HttpClient.Builder connectTimeout(Duration duration) {
        return builder.connectTimeout(duration);
    }

    @Override
    public HttpClient.Builder sslContext(SSLContext sslContext) {
        return builder.sslContext(sslContext);
    }

    @Override
    public HttpClient.Builder sslParameters(SSLParameters sslParameters) {
        return builder.sslParameters(sslParameters);
    }

    @Override
    public HttpClient.Builder executor(Executor executor) {
        return builder.executor(executor);
    }

    @Override
    public HttpClient.Builder followRedirects(HttpClient.Redirect policy) {
        return builder.followRedirects(policy);
    }

    @Override
    public HttpClient.Builder version(HttpClient.Version version) {
        return builder.version(version);
    }

    @Override
    public HttpClient.Builder priority(int priority) {
        return builder.priority(priority);
    }

    @Override
    public HttpClient.Builder proxy(ProxySelector proxySelector) {
        return builder.proxy(proxySelector);
    }

    @Override
    public HttpClient.Builder authenticator(Authenticator authenticator) {
        return builder.authenticator(authenticator);
    }

    @Override
    public HttpClient build() {
        return builder.build();
    }
}
