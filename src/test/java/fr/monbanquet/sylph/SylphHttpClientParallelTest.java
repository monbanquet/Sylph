package fr.monbanquet.sylph;

import fr.monbanquet.sylph.helpers.ObjectToString;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.helpers.TodoBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpStatusCode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
public class SylphHttpClientParallelTest {

    private static final String BASE_URL = "http://localhost:1080";
    private static final String PATH_URL = "/the-path";
    private static final String URL = BASE_URL + PATH_URL;

    private final ClientAndServer client;

    public SylphHttpClientParallelTest(ClientAndServer client) {
        this.client = client;

        // init server response
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(i -> {
                    Todo todo = TodoBuilder.newTodo();
                    todo.setId(i);
                    client.when(org.mockserver.model.HttpRequest.request()
                            .withPath(PATH_URL + "/" + i))
                            .respond(org.mockserver.model.HttpResponse.response()
                                    .withStatusCode(HttpStatusCode.OK_200.code())
                                    .withBody(ObjectToString.toString(todo)));
                });
    }

    private void parallel(IntConsumer func) {
        IntStream.rangeClosed(1, 100)
                .parallel()
                .forEach(func);
    }

    private void parallelAsync(IntFunction<CompletableFuture> func) {
        IntStream.rangeClosed(1, 100)
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
                    .uri(URI.create(URL + "/" + i))
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
                    .uri(URI.create(URL + "/" + i))
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
                    .GET(URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            Assertions.assertThat(todo.getId()).isEqualTo(i);
        });
    }

    @Test
    void parallel_stream_sync_request_when_new_sylph_builder_outside_and_new_client_inside_loop() {
        Sylph sylph = Sylph.builder();
        parallel(i -> {
            Todo todo = sylph.getClient()
                    .GET(URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            Assertions.assertThat(todo.getId()).isEqualTo(i);
        });
    }

    @Test
    void parallel_stream_sync_request_when_new_client_outside_loop() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        parallel(i -> {
            Todo todo = sylphHttpClient
                    .GET(URL + "/" + i)
                    .send(Todo.class)
                    .asObject();
            Assertions.assertThat(todo.getId()).isEqualTo(i);
        });
    }

    // ---  --- //

    @Test
    void parallel_stream_async_request_when_new_client_inside_loop() {
        parallelAsync(i -> Sylph.newClient()
                .GET(URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    Assertions.assertThat(todo.getId()).isEqualTo(i);
                }));
    }

    @Test
    void parallel_stream_async_request_when_new_sylph_builder_outside_and_new_client_inside_loop() {
        Sylph sylph = Sylph.builder();
        parallelAsync(i -> sylph.getClient()
                .GET(URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    Assertions.assertThat(todo.getId()).isEqualTo(i);
                }));
    }

    @Test
    void parallel_stream_async_request_when_new_client_outside_loop() {
        SylphHttpClient sylphHttpClient = Sylph.newClient();
        parallelAsync(i -> sylphHttpClient
                .GET(URL + "/" + i)
                .sendAsync(Todo.class)
                .thenAccept(response -> {
                    Todo todo = response.asObject();
                    Assertions.assertThat(todo.getId()).isEqualTo(i);
                }));
    }

}
