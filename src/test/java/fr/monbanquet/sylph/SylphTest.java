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
import fr.monbanquet.sylph.helpers.ObjectToString;
import fr.monbanquet.sylph.helpers.Todo;
import fr.monbanquet.sylph.helpers.TodoBuilder;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
public class SylphTest {

    protected static final String BASE_URL = "http://localhost:1080";
    protected static final String PATH_URL = "/the-path";
    protected static final String URL = BASE_URL + PATH_URL;

    protected static final Parser parser = DefaultParser.create();

    private final ClientAndServer client;

    public SylphTest(ClientAndServer client) {
        this.client = client;
    }

    @BeforeEach
    public void init() {
        Todo todo = TodoBuilder.newTodo();
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(ObjectToString.toString(todo)));
    }

    @Test
    void builder_like_standard_client() throws IOException, InterruptedException {
        /** given **/
        // standard client
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json; charset=utf-8")
                .uri(URI.create(URL))
                .GET()
                .copy()
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.ofSeconds(5))
                .build();
        HttpClient client = HttpClient.newBuilder()
                .priority(1)
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        // sylph client
        SylphHttpClient sylph = Sylph.builder()
                .setBaseRequest(SylphHttpRequest.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .uri(URI.create(URL))
                        .GET()
                        .copy()
                        .version(HttpClient.Version.HTTP_2)
                        .timeout(Duration.ofSeconds(5)))
                .setClient(SylphHttpClient.newBuilder()
                        .priority(1)
                        .version(HttpClient.Version.HTTP_2)
                        .followRedirects(HttpClient.Redirect.ALWAYS)
                )
                .getClient();

        /** when **/
        // standard client
        HttpResponse<String> responseStandard = client.send(request, HttpResponse.BodyHandlers.ofString());
        // sylph client
        SylphHttpResponse<String, Todo> responseSylph = sylph.send(Todo.class);

        /** then **/
        // standard client
        String body = responseStandard.body();
        Todo todo = parser.deserialize(body, Todo.class);
        AssertTodo.assertResult(todo);
        // sylph client
        AssertTodo.assertResult(responseSylph.asObject());
    }

}

