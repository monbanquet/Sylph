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

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

public class SylphHttpResponse<T> implements HttpResponse<T> {

    private final HttpResponse<T> response;

    public SylphHttpResponse(HttpResponse<T> response) {
        this.response = response;
    }

    public static class BodyHandlers {

        private BodyHandlers() {
        }

        public static <T> HttpResponse.BodyHandler<T> ofObject(Class<T> returnType, Parser parser) {
            return (responseInfo) -> BodySubscribers.ofObject(Charset.forName("UTF8"), returnType, parser);
        }

        public static <T> HttpResponse.BodyHandler<List<T>> ofList(Class<T> returnType, Parser parser) {
            return (responseInfo) -> BodySubscribers.ofList(Charset.forName("UTF8"), returnType, parser);
        }

    }

    public static class BodySubscribers {

        private BodySubscribers() {
        }

        public static <T> HttpResponse.BodySubscriber<T> ofObject(Charset charset, Class<T> returnType, Parser parser) {
            return new ObjectBodySubscriber<>(charset, returnType, parser);
        }

        public static <T> HttpResponse.BodySubscriber<List<T>> ofList(Charset charset, Class<T> returnType, Parser parser) {
            return new ListBodySubscriber<>(charset, returnType, parser);
        }

    }

    // --- Delegate HttpResponse --- //

    @Override
    public int statusCode() {
        return response.statusCode();
    }

    @Override
    public HttpRequest request() {
        return response.request();
    }

    @Override
    public Optional<HttpResponse<T>> previousResponse() {
        return response.previousResponse();
    }

    @Override
    public HttpHeaders headers() {
        return response.headers();
    }

    @Override
    public T body() {
        return response.body();
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return response.sslSession();
    }

    @Override
    public URI uri() {
        return response.uri();
    }

    @Override
    public HttpClient.Version version() {
        return response.version();
    }
}
