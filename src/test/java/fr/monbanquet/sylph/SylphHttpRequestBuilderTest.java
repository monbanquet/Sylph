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

import fr.monbanquet.sylph.helpers.Helper;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static fr.monbanquet.sylph.SylphHttpRequestBuilder.HEADER_CONTENT_TYPE;
import static fr.monbanquet.sylph.SylphHttpRequestBuilder.HEADER_CONTENT_TYPE_VALUE;

class SylphHttpRequestBuilderTest {

    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";

    private static final Parser parser = DefaultParser.create();

    @Test
    void post_should_throws_exception_when_no_parser() {
        Assertions.assertThrows(NullPointerException.class, () ->
                SylphHttpRequest.newBuilder(TODOS_URL)
                        .POST(Helper.newTodo())
                        .build());
    }

    @Test
    void post_with_todo_object() {
        // given
        Todo todo = Helper.newTodo();
        SylphHttpRequest request = SylphHttpRequest.newBuilder(TODOS_URL)
                .parser(DefaultParser.create())
                .POST(todo)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void post_with_todo_json_string() {
        // given
        Todo todo = Helper.newTodo();
        String todoJsonString = parser.serialize(todo);
        SylphHttpRequest request = SylphHttpRequest.newBuilder(TODOS_URL)
                .POST(todoJsonString)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void put_with_todo_object() {
        // given
        Todo todo = Helper.newTodo();
        SylphHttpRequest request = SylphHttpRequest.newBuilder(TODOS_URL + "/" + todo.getId())
                .parser(DefaultParser.create())
                .PUT(todo)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void put_with_todo_json_string() {
        // given
        Todo todo = Helper.newTodo();
        String todoJsonString = parser.serialize(todo);
        SylphHttpRequest request = SylphHttpRequest.newBuilder(TODOS_URL + "/" + todo.getId())
                .PUT(todoJsonString)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void request_should_have_default_content_type_if_not_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com").build();
        Assertions.assertEquals(1, request.headers().allValues(HEADER_CONTENT_TYPE).size());
        Assertions.assertEquals(HEADER_CONTENT_TYPE_VALUE, request.headers().firstValue(HEADER_CONTENT_TYPE).get());
    }

    @Test
    void request_should_have_content_type_if_set() {
        String contentTypeValue = "ABC";
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .header(HEADER_CONTENT_TYPE, contentTypeValue)
                .build();
        Assertions.assertEquals(1, request.headers().allValues(HEADER_CONTENT_TYPE).size());
        Assertions.assertEquals(contentTypeValue, request.headers().firstValue(HEADER_CONTENT_TYPE).get());
    }

    @Test
    void request_should_have_default_content_type_if_other_header_is_set() {
        String headerKey = "123";
        String headerValue = "ABC";
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .header(headerKey, headerValue)
                .build();
        Assertions.assertEquals(1, request.headers().allValues(HEADER_CONTENT_TYPE).size());
        Assertions.assertEquals(HEADER_CONTENT_TYPE_VALUE, request.headers().firstValue(HEADER_CONTENT_TYPE).get());
        Assertions.assertEquals(1, request.headers().allValues(headerKey).size());
        Assertions.assertEquals(headerValue, request.headers().firstValue(headerKey).get());
    }

    @Test
    void request_should_have_default_timeout_if_not_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com").build();
        Assertions.assertEquals(Duration.ofSeconds(30), request.timeout().get());
    }

    @Test
    void request_should_have_timeout_if_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .timeout(Duration.ofHours(24))
                .build();
        Assertions.assertEquals(Duration.ofHours(24), request.timeout().get());
    }
}