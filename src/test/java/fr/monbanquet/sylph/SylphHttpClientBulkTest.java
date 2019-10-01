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

import fr.monbanquet.sylph.helpers.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

class SylphHttpClientBulkTest {

    private static final Logger log = LoggerFactory.getLogger(SylphHttpClientBulkTest.class);

    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";

    @Test
    void parallel() {
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(i -> {
                    log.info("Start request {}", i);
                    Todo todo = Sylph.newClient()
                            .GET(TODOS_URL + "/" + i)
                            .send(Todo.class)
                            .asObject();
                    log.info("End request {}", i);
                    Assertions.assertEquals(todo.getId(), i);
                });
    }

    @Test
    void parallel_async() {
        IntStream.rangeClosed(1, 100)
                .parallel()
                .mapToObj(i -> {
                    log.info("Start request {}", i);
                    return Sylph.newClient()
                            .GET(TODOS_URL + "/" + i)
                            .sendAsync(Todo.class)
                            .thenAccept(response -> {
                                log.info("End request {}", i);
                                Todo todo = response.asObject();
                                Assertions.assertEquals(todo.getId(), i);
                            });
                })
                .forEach(CompletableFuture::join);
    }

}