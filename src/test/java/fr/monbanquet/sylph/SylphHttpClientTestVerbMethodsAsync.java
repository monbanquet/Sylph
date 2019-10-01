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
import fr.monbanquet.sylph.helpers.Helper;
import fr.monbanquet.sylph.helpers.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

class SylphHttpClientTestVerbMethodsAsync {

    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";
    private static final String TODO_1_URL = TODOS_URL + "/1";

    @Test
    void get_with_url_string_param() {
        // when
        Todo todo = Sylph.newClient()
                .GET(TODO_1_URL)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        AssertTodo.assertResult(todo);
    }

    @Test
    void get_with_uri_param() {
        // when
        Todo todo = Sylph.newClient()
                .GET(URI.create(TODO_1_URL))
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        AssertTodo.assertResult(todo);
    }

    @Test
    void post_with_url_string_param() {
        // when
        Todo todoResult = Sylph.newClient()
                .POST(TODOS_URL)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), 0);
        Assertions.assertEquals(todoResult.getUserId(), 0);
        Assertions.assertNull(todoResult.getTitle());
        Assertions.assertFalse(todoResult.isCompleted());
    }

    @Test
    void post_with_uri_param() {
        // when
        Todo todoResult = newClient()
                .POST(URI.create(TODOS_URL))
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), 0);
        Assertions.assertEquals(todoResult.getUserId(), 0);
        Assertions.assertNull(todoResult.getTitle());
        Assertions.assertFalse(todoResult.isCompleted());
    }

    @Test
    void post_with_url_string_param_and_body_object_param() {
        // given
        Todo todo = Helper.newTodo();

        // when
        Todo todoResult = newClient()
                .POST(TODOS_URL, todo)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void post_with_uri_param_and_body_object_param() {
        // given
        Todo todo = Helper.newTodo();

        // when
        Todo todoResult = newClient()
                .POST(URI.create(TODOS_URL), todo)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertNotEquals(todoResult.getId(), todo.getId());
    }

    @Test
    void put_with_url_string_param() {
        // given
        int id = 42;

        // when
        Todo todoResult = newClient()
                .PUT(TODOS_URL + "/" + id)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), id);
    }

    @Test
    void put_with_uri_param() {
        // given
        int id = 42;

        // when
        Todo todoResult = newClient()
                .PUT(TODOS_URL + "/" + id)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), id);
    }

    @Test
    void put_with_url_string_param_and_body_object_param() {
        // given
        int id = 42;
        Todo todo = Helper.newTodo();

        // when
        Todo todoResult = newClient()
                .PUT(TODOS_URL + "/" + id, todo)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), id);
    }

    @Test
    void put_with_uri_param_and_body_object_param() {
        // given
        int id = 42;
        Todo todo = Helper.newTodo();

        // when
        Todo todoResult = newClient()
                .PUT(URI.create(TODOS_URL + "/" + id), todo)
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), id);
    }

    @Test
    void delete_with_string_param() {
        // when
        Todo todoResult = newClient()
                .DELETE(TODOS_URL + "/44")
                .bodyAsync(Todo.class)
                .join();

        // then
        Assertions.assertEquals(todoResult.getId(), 0);
    }

    @Test
    void delete_with_uri_param() {
        // when
        Todo todoResult = newClient()
                .DELETE(URI.create(TODOS_URL + "/44"))
                .sendAsync(Todo.class)
                .join()
                .asObject();

        // then
        Assertions.assertEquals(todoResult.getId(), 0);
    }

    private SylphHttpClient newClient() {
        return Sylph.builder()
                .getClient();
    }


}