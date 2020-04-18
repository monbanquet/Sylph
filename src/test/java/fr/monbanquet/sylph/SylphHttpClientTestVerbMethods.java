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

import fr.monbanquet.sylph.exception.SylphHttpResponseException;
import fr.monbanquet.sylph.helpers.AssertTodo;
import fr.monbanquet.sylph.helpers.ObjectToString;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.helpers.TodoBuilder;
import io.netty.handler.codec.http.HttpMethod;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
class SylphHttpClientTestVerbMethods {

    private static final String BASE_URL = "http://localhost:1080";
    private static final String PATH_URL = "/the-path";
    private static final String URL = BASE_URL + PATH_URL;

    private final ClientAndServer client;

    public SylphHttpClientTestVerbMethods(ClientAndServer client) {
        this.client = client;
    }

    @BeforeEach
    public void init() {
        client.reset();
    }

    @Test
    void get_with_url_string_param() {
        // given
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath(PATH_URL))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(TodoBuilder.newTodo())));

        // when
        Todo todoResult = Sylph
                .GET(URL)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void get_with_uri_param() {
        // given
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath(PATH_URL))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(TodoBuilder.newTodo())));

        // when
        Todo todoResult = Sylph
                .GET(URI.create(URL))
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void post_with_url_string_param() {
        // given
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.POST.name())
                .withPath(PATH_URL))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(TodoBuilder.newTodo())));

        // when
        Todo todoResult = Sylph
                .POST(URL)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void post_with_uri_param() {
        // given
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.POST.name())
                .withPath(PATH_URL))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(TodoBuilder.newTodo())));

        // when
        Todo todoResult = Sylph
                .POST(URI.create(URL))
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void post_with_url_string_param_and_body_object_param() {
        // given
        int id = 41;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        String todoString = ObjectToString.toString(todo);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.POST.name())
                .withPath(PATH_URL))
                .respond(httpRequest -> {
                    if (httpRequest.getBodyAsString().equals(todoString)) {
                        return HttpResponse.response()
                                .withStatusCode(HttpStatusCode.OK_200.code())
                                .withBody(todoString);
                    } else {
                        return HttpResponse.response()
                                .withStatusCode(HttpStatusCode.BAD_REQUEST_400.code());
                    }
                });

        // when
        Todo todoResult = Sylph
                .POST(URL, todo)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void post_with_uri_param_and_body_object_param() {
        // given
        int id = 41;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        String todoString = ObjectToString.toString(todo);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.POST.name())
                .withPath(PATH_URL))
                .respond(httpRequest -> {
                    if (httpRequest.getBodyAsString().equals(todoString)) {
                        return HttpResponse.response()
                                .withStatusCode(HttpStatusCode.OK_200.code())
                                .withBody(todoString);
                    } else {
                        return HttpResponse.response()
                                .withStatusCode(HttpStatusCode.BAD_REQUEST_400.code());
                    }
                });

        // when
        Todo todoResult = Sylph
                .POST(URI.create(URL), todo)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void post_with_body_object_param_throw_error() {
        // given
        int codeError = HttpStatusCode.INTERNAL_SERVER_ERROR_500.code();
        String bodyError = "INTERNAL ERROR";
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.POST.name())
                .withPath(PATH_URL))
                .respond(HttpResponse.response()
                        .withStatusCode(codeError)
                        .withBody(bodyError));

        // when
        AbstractThrowableAssert<?, ? extends Throwable> catchError = assertThatThrownBy(() ->
                Sylph
                        .POST(URI.create(URL), TodoBuilder.newTodo())
                        .send(Todo.class)
                        .asObject());

        // then
        catchError.isInstanceOf(SylphHttpResponseException.class)
                .hasMessageContainingAll(String.valueOf(codeError), bodyError)
                .hasFieldOrPropertyWithValue("responseCode", codeError)
                .hasFieldOrPropertyWithValue("errorBody", bodyError);
    }

    @Test
    void put_with_url_string_param() {
        // given
        int id = 42;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.PUT.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .PUT(URL + "/" + id)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void put_with_uri_param() {
        // given
        int id = 42;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.PUT.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .PUT(URL + "/" + id)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void put_with_url_string_param_and_body_object_param() {
        // given
        int id = 42;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.PUT.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .PUT(URL + "/" + id)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void put_with_uri_param_and_body_object_param() {
        // given
        int id = 42;
        Todo todo = TodoBuilder.newTodo();
        todo.setId(id);
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.PUT.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .PUT(URI.create(URL + "/" + id), todo)
                .send(Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult, id);
    }

    @Test
    void delete_with_string_param() {
        // given
        int id = 44;
        Todo todo = new Todo();
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.DELETE.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .DELETE(URL + "/44")
                .body(Todo.class);

        // then
        assertThat(todoResult.getId()).isEqualTo(0);
    }

    @Test
    void delete_with_uri_param() {
        // given
        int id = 44;
        Todo todo = new Todo();
        client.when(HttpRequest.request()
                .withMethod(HttpMethod.DELETE.name())
                .withPath(PATH_URL + "/" + id))
                .respond(httpRequest -> HttpResponse.response()
                        .withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(ObjectToString.toString(todo)));

        // when
        Todo todoResult = Sylph
                .DELETE(URI.create(URL + "/44"))
                .send(Todo.class)
                .asObject();

        // then
        assertThat(todoResult.getId()).isEqualTo(0);
    }

}