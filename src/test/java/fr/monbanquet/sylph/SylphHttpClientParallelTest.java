package fr.monbanquet.sylph;

import fr.monbanquet.sylph.helpers.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SylphHttpClientParallelTest {

    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";

    private void parallel(IntConsumer func) {
        IntStream.rangeClosed(1, 200)
                .parallel()
                .forEach(func);
    }

    private void parallelAsync(IntFunction<CompletableFuture> func) {
        IntStream.rangeClosed(1, 200)
                .parallel()
                .mapToObj(func)
                .forEach(CompletableFuture::join);
    }

    // ---  --- //

    @Test
    void parallel_stream_sync_request_with_standard_http_client() {
        HttpClient httpClient = HttpClient.newBuilder().build();
        parallel(i -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TODOS_URL + "/" + i))
                    .GET()
                    .build();
            Assertions.assertThatCode(() -> {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                Assertions.assertThat(response.body()).contains(String.valueOf(i));
            }).doesNotThrowAnyException();
        });
    }

    @Test
    void parallel_stream_async_request_with_standard_http_client() {
        HttpClient httpClient = HttpClient.newBuilder().build();
        parallel(i -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TODOS_URL + "/" + i))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            Assertions.assertThat(response.body()).contains(String.valueOf(i));
        });
    }

    // ---  --- //

    @Test
    void parallel_stream_sync_request_when_new_client_inside_loop() {
        parallel(i -> {
            Todo todo = Sylph.newClient()
                    .GET(TODOS_URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            assertEquals(todo.getId(), i);
        });
    }

    @Test
    void parallel_stream_sync_request_when_new_sylph_builder_outside_and_new_client_inside_loop() {
        Sylph sylph = Sylph.builder();
        parallel(i -> {
            Todo todo = sylph.getClient()
                    .GET(TODOS_URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            assertEquals(todo.getId(), i);
        });
    }

    @Test
    void parallel_stream_sync_request_when_new_client_outside_loop() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        parallel(i -> {
            Todo todo = sylphHttpClient
                    .GET(TODOS_URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            assertEquals(todo.getId(), i);
        });
    }

    // ---  --- //

    @Test
    void parallel_stream_async_request_when_new_client_inside_loop() {
        parallelAsync(i -> Sylph.newClient()
                .GET(TODOS_URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    assertEquals(todo.getId(), i);
                }));
    }

    @Test
    void parallel_stream_async_request_when_new_sylph_builder_outside_and_new_client_inside_loop() {
        Sylph sylph = Sylph.builder();
        parallelAsync(i -> sylph.getClient()
                .GET(TODOS_URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    assertEquals(todo.getId(), i);
                }));
    }

    @Test
    void parallel_stream_async_request_when_new_client_outside_loop() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        parallelAsync(i -> sylphHttpClient
                .GET(TODOS_URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    assertEquals(todo.getId(), i);
                }));
    }

}
