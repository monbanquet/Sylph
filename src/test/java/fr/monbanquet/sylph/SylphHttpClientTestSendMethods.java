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

import fr.monbanquet.sylph.helpers.AssertTodo;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class SylphHttpClientTestSendMethods {

    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";
    private static final String TODO_1_URL = TODOS_URL + "/1";

    private static final Parser parser = DefaultParser.create();

    @Test
    void send_with_request_param_and_responseBodyHandler_param() {
        // given
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create(TODO_1_URL))
                .GET()
                .copy()
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(5))
                .build();
        SylphHttpClient client = SylphHttpClient.newHttpClient();

        // when
        SylphHttpResponse<String, String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        String body = response.body();
        Todo todo = parser.deserialize(body, Todo.class);
        AssertTodo.assertResult(todo);
    }


    @Test
    void send_with_responseBodyHandler_param() {
        // given
        SylphHttpClient client = Sylph.builder()
                .setBaseRequest(SylphHttpRequest.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .uri(URI.create(TODO_1_URL))
                        .GET()
                        .copy()
                        .version(HttpClient.Version.HTTP_2)
                        .timeout(Duration.ofSeconds(5)))
                .getClient();

        // when
        SylphHttpResponse<String, String> response = client.send(HttpResponse.BodyHandlers.ofString());

        // then
        String body = response.body();
        Todo todo = parser.deserialize(body, Todo.class);
        AssertTodo.assertResult(todo);
    }


    @Test
    void send_with_request_param_and_returnType_param() {
        // given
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create(TODO_1_URL))
                .GET()
                .copy()
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(5))
                .build();
        SylphHttpClient client = SylphHttpClient.newHttpClient();

        // when
        SylphHttpResponse<String, Todo> response = client.send(request, Todo.class);

        // then
        Todo todo = response.asObject();
        AssertTodo.assertResult(todo);
    }

    @Test
    void send_with_returnType_object_param() {
        // when
        Todo todo = Sylph.newClient()
                .GET(TODO_1_URL)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todo);
    }

    @Test
    void send_with_returnType_list_param() {
        // when
        Todo todo = Sylph.newClient()
                .GET(TODO_1_URL)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todo);
    }

    @Test
    void body_with_returnType_param() {
        // when
        Todo todo = Sylph.newClient()
                .GET(TODO_1_URL)
                .body(Todo.class);

        // then
        AssertTodo.assertResult(todo);
    }

    @Test
    void bodyList_with_returnType_param() {
        // when
        List<Todo> todos = Sylph.newClient()
                .GET(TODOS_URL)
                .bodyList(Todo.class);

        // then
        AssertTodo.assertResult(todos);
    }

}

