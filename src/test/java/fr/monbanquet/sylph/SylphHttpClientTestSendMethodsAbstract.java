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

import fr.monbanquet.sylph.exception.SylphHttpRequestException;
import fr.monbanquet.sylph.helpers.AssertTodo;
import fr.monbanquet.sylph.helpers.ObjectToString;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.helpers.TodoBuilder;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
public abstract class SylphHttpClientTestSendMethodsAbstract {

    protected static final String BASE_URL = "http://localhost:1080";
    protected static final String PATH_URL = "/the-path";
    protected static final String URL = BASE_URL + PATH_URL;

    protected static final Parser parser = DefaultParser.create();

    private final ClientAndServer client;

    public SylphHttpClientTestSendMethodsAbstract(ClientAndServer client) {
        this.client = client;
    }

    @BeforeEach
    public void init() {
        client.reset();
    }

    @Test
    void send_with_request_param_and_responseBodyHandler() {
        // given
        Todo todo = TodoBuilder.newTodo();
        defaultMockserver(ObjectToString.toString(todo));
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create(URL))
                .GET()
                .copy()
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(5))
                .build();
        SylphHttpClient client = SylphHttpClient.newHttpClient();

        // when
        SylphHttpResponse<String, String> response = call_send_with_request_param_and_responseBodyHandler(request, client);

        // then
        String body = response.body();
        Todo todoResult = parser.deserialize(body, Todo.class);
        AssertTodo.assertResult(todoResult);
    }

    protected abstract SylphHttpResponse<String, String> call_send_with_request_param_and_responseBodyHandler(HttpRequest request, SylphHttpClient client);

    @Test
    void send_with_responseBodyHandler() {
        // given
        Todo todo = TodoBuilder.newTodo();
        defaultMockserver(ObjectToString.toString(todo));
        SylphHttpClient client = Sylph.builder()
                .setBaseRequest(SylphHttpRequest.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .uri(URI.create(URL))
                        .GET()
                        .copy()
                        .version(HttpClient.Version.HTTP_2)
                        .timeout(Duration.ofSeconds(5)))
                .getClient();

        // when
        SylphHttpResponse<String, String> response = call_send_with_responseBodyHandler(client);

        // then
        String body = response.body();
        Todo todoResult = parser.deserialize(body, Todo.class);
        AssertTodo.assertResult(todoResult);
    }

    protected abstract SylphHttpResponse<String, String> call_send_with_responseBodyHandler(SylphHttpClient client);

    @Test
    void send_with_request_param_and_returnType_param() {
        // given
        Todo todo = TodoBuilder.newTodo();
        defaultMockserver(ObjectToString.toString(todo));
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create(URL))
                .GET()
                .copy()
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(5))
                .build();
        SylphHttpClient client = SylphHttpClient.newHttpClient();

        // when
        SylphHttpResponse<String, Todo> response = call_send_with_request_param_and_returnType_param(request, client);

        // then
        Todo todoResult = response.asObject();
        AssertTodo.assertResult(todoResult);
    }

    protected abstract SylphHttpResponse<String, Todo> call_send_with_request_param_and_returnType_param(HttpRequest request, SylphHttpClient client);

    @Test
    void send_with_returnType_object() {
        // given
        Todo todo = TodoBuilder.newTodo();
        defaultMockserver(ObjectToString.toString(todo));

        // when
        Todo todoResult = call_send_with_returnType_object();

        // then
        AssertTodo.assertResult(todoResult);
    }

    protected abstract Todo call_send_with_returnType_object();

    @Test
    void send_with_returnType_list() {
        // given
        List<Todo> todos = List.of(TodoBuilder.newTodo(), TodoBuilder.newTodo());
        defaultMockserver(ObjectToString.toString(todos));

        // when
        List<Todo> todoResults = call_send_with_returnType_list();

        // then
        AssertTodo.assertResult(todoResults);
    }

    protected abstract List<Todo> call_send_with_returnType_list();

    @Test
    void body_with_returnType() {
        // given
        Todo todo = TodoBuilder.newTodo();
        defaultMockserver(ObjectToString.toString(todo));

        // when
        Todo todoResult = call_body_with_returnType();

        // then
        AssertTodo.assertResult(todoResult);
    }

    protected abstract Todo call_body_with_returnType();

    @Test
    void bodyList_with_returnType() {
        // given
        List<Todo> todos = List.of(TodoBuilder.newTodo(), TodoBuilder.newTodo());
        defaultMockserver(ObjectToString.toString(todos));

        // when
        List<Todo> todoResults = call_bodyList_with_returnType();

        // then
        AssertTodo.assertResult(todoResults);
    }

    protected abstract List<Todo> call_bodyList_with_returnType();

    @Test
    void send_with_wrong_uri_scheme_should_throws_error() {
        // when
        AbstractThrowableAssert<?, ? extends Throwable> catchError = assertThatThrownBy(() -> call_send_with_wrong_uri_scheme_should_throws_error());

        // then
        catchError.isInstanceOf(IllegalArgumentException.class);
    }

    protected abstract void call_send_with_wrong_uri_scheme_should_throws_error();

    @Test
    void send_with_wrong_not_exists_should_throws_error() {
        // when
        AbstractThrowableAssert<?, ? extends Throwable> catchError = assertThatThrownBy(() -> call_send_with_uri_not_exists_should_throws_error());

        // then
        catchError.isInstanceOf(SylphHttpRequestException.class);
    }

    protected abstract void call_send_with_uri_not_exists_should_throws_error();

    private void defaultMockserver(String s) {
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(s));
    }
}

