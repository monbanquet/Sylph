package fr.monbanquet.sylph;

import fr.monbanquet.sylph.helpers.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SylphHttpClientParallelTest {

    private static final Logger log = LoggerFactory.getLogger(SylphHttpClientParallelTest.class);
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
                    assertEquals(todo.getId(), i);
                });
    }

    @Test
    void parallel_2() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(i -> {
                    log.info("Start request {}", i);
                    Todo todo = sylphHttpClient
                            .GET(TODOS_URL + "/" + i)
                            .send(Todo.class)
                            .asObject();
                    log.info("End request {}", i);
                    assertEquals(todo.getId(), i);
                });
    }

    @Test
    void parallel_3() {
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(i -> {
                    log.info("Start request {}", i);
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(TODOS_URL + "/" + i))
                            .GET()
                            .build();
                    try {
                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        log.info("End request {}", i);
                        Assertions.assertThat(response.body()).contains(String.valueOf(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                                assertEquals(todo.getId(), i);
                            });
                })
                .forEach(CompletableFuture::join);
    }

    @Test
    void parallel_async_2() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        IntStream.rangeClosed(1, 100)
                .parallel()
                .mapToObj(i -> {
                    log.info("Start request {}", i);
                    return sylphHttpClient
                            .GET(TODOS_URL + "/" + i)
                            .sendAsync(Todo.class)
                            .thenAccept(response -> {
                                log.info("End request {}", i);
                                Todo todo = response.asObject();
                                assertEquals(todo.getId(), i);
                            });
                })
                .forEach(CompletableFuture::join);
    }

    @Test
    void parallel_async_3() {
        HttpClient httpClient = HttpClient.newBuilder()
                .build();
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(i -> {
                    log.info("Start request {}", i);
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(TODOS_URL + "/" + i))
                            .GET()
                            .build();
                    HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
                    Assertions.assertThat(response.body()).contains(String.valueOf(i));
                });
    }


}
