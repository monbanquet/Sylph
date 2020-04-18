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
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.monbanquet.sylph.SylphHttpRequestBuilder.HEADER_CONTENT_TYPE;
import static fr.monbanquet.sylph.SylphHttpRequestBuilder.HEADER_CONTENT_TYPE_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
class SylphHttpRequestBuilderTest {

    private static final String BASE_URL = "http://localhost:1080";
    private static final String PATH_URL = "/the-path";
    private static final String URL = BASE_URL + PATH_URL;

    private static final Parser parser = DefaultParser.create();

    private final ClientAndServer client;

    public SylphHttpRequestBuilderTest(ClientAndServer client) {
        this.client = client;
    }

    @BeforeEach
    public void init() {
        client.reset();
    }

    @Test
    void post_should_throws_exception_when_no_parser() {
        // when
        AbstractThrowableAssert<?, ? extends Throwable> catchError = assertThatThrownBy(() ->
                SylphHttpRequest.newBuilder(URL)
                        .POST(TodoBuilder.newTodo())
                        .build());

        // then
        catchError.isInstanceOf(NullPointerException.class);
    }

    @Test
    void post_with_todo_object() {
        // given
        Todo todo = TodoBuilder.newTodo();
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(ObjectToString.toString(todo)));
        SylphHttpRequest request = SylphHttpRequest.newBuilder(URL)
                .parser(DefaultParser.create())
                .POST(todo)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void post_with_todo_json_string() {
        // given
        Todo todo = TodoBuilder.newTodo();
        String todoJsonString = parser.serialize(todo);
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(ObjectToString.toString(todo)));
        SylphHttpRequest request = SylphHttpRequest.newBuilder(URL)
                .POST(todoJsonString)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void put_with_todo_object() {
        // given
        Todo todo = TodoBuilder.newTodo();
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(ObjectToString.toString(todo)));
        SylphHttpRequest request = SylphHttpRequest.newBuilder(URL)
                .parser(DefaultParser.create())
                .PUT(todo)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        AssertTodo.assertResult(todoResult);
    }

    @Test
    void put_with_todo_json_string() {
        // given
        Todo todo = TodoBuilder.newTodo();
        String todoJsonString = parser.serialize(todo);
        client.when(org.mockserver.model.HttpRequest.request()
                .withPath(PATH_URL))
                .respond(org.mockserver.model.HttpResponse.response()
                        .withBody(ObjectToString.toString(todo)));
        SylphHttpRequest request = SylphHttpRequest.newBuilder(URL)
                .PUT(todoJsonString)
                .build();

        // when
        Todo todoResult = SylphHttpClient.newHttpClient()
                .send(request, Todo.class)
                .asObject();

        // then
        assertThat(todoResult.getId()).isEqualTo(todo.getId());
    }

    @Test
    void request_should_have_default_content_type_if_not_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com").build();
        assertThat(1).isEqualTo(request.headers().allValues(HEADER_CONTENT_TYPE).size());
        assertThat(HEADER_CONTENT_TYPE_VALUE).isEqualTo(request.headers().firstValue(HEADER_CONTENT_TYPE).get());
    }

    @Test
    void request_should_have_content_type_if_set() {
        String contentTypeValue = "ABC";
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .header(HEADER_CONTENT_TYPE, contentTypeValue)
                .build();
        assertThat(1).isEqualTo(request.headers().allValues(HEADER_CONTENT_TYPE).size());
        assertThat(contentTypeValue).isEqualTo(request.headers().firstValue(HEADER_CONTENT_TYPE).get());
    }

    @Test
    void request_should_have_default_content_type_if_other_header_is_set() {
        String headerKey = "123";
        String headerValue = "ABC";
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .header(headerKey, headerValue)
                .build();
        assertThat(1).isEqualTo(request.headers().allValues(HEADER_CONTENT_TYPE).size());
        assertThat(HEADER_CONTENT_TYPE_VALUE).isEqualTo(request.headers().firstValue(HEADER_CONTENT_TYPE).get());
        assertThat(1).isEqualTo(request.headers().allValues(headerKey).size());
        assertThat(headerValue).isEqualTo(request.headers().firstValue(headerKey).get());
    }

    @Test
    void request_should_have_default_timeout_if_not_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com").build();
        assertThat(Duration.ofSeconds(30)).isEqualTo(request.timeout().get());
    }

    @Test
    void request_should_have_timeout_if_set() {
        SylphHttpRequest request = SylphHttpRequest.newBuilder("https://fake.com")
                .timeout(Duration.ofHours(24))
                .build();
        assertThat(Duration.ofHours(24)).isEqualTo(request.timeout().get());
    }


    @Test
    void copy_should_copy_all_parameters() {
        // given
        String uri = "https://monbanquet.fr";
        String headerKey1 = "header-key1";
        String headerValue1 = "header-value1";
        String headerKey2 = "header-key2";
        String headerValue2 = "header-value2";
        String method = "POST";
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString("{}");
        boolean expectContinue = true;
        Duration timeout = Duration.ofSeconds(456);
        HttpClient.Version version = HttpClient.Version.HTTP_2;

        SylphHttpRequestBuilder builder = SylphHttpRequestBuilder.newBuilder()
                .uri(uri)
                .header(headerKey1, headerValue1)
                .header(headerKey2, headerValue2)
                .method(method, bodyPublisher)
                .expectContinue(expectContinue)
                .timeout(timeout)
                .version(version);

        // when
        SylphHttpRequest copy = builder.copy()
                .build();

        // then
        assertThat(uri).isEqualTo(copy.uri().toString());

        Map<String, List<String>> headersMap = copy.headers().map();
        List<String> keys = headersMap.keySet().stream().collect(Collectors.toList());

        assertThat(3).isEqualTo(keys.size());

        // always contains Content-Type header
        assertThat(HEADER_CONTENT_TYPE).isEqualTo(keys.get(0));
        assertThat(1).isEqualTo(headersMap.get(HEADER_CONTENT_TYPE).size());
        assertThat(HEADER_CONTENT_TYPE_VALUE).isEqualTo(headersMap.get(HEADER_CONTENT_TYPE).get(0));

        assertThat(headerKey1).isEqualTo(keys.get(1));
        assertThat(1).isEqualTo(headersMap.get(headerKey1).size());
        assertThat(headerValue1).isEqualTo(headersMap.get(headerKey1).get(0));

        assertThat(headerKey2).isEqualTo(keys.get(2));
        assertThat(1).isEqualTo(headersMap.get(headerKey2).size());
        assertThat(headerValue2).isEqualTo(headersMap.get(headerKey2).get(0));

        assertThat(method).isEqualTo(copy.method());

        assertThat(bodyPublisher).isEqualTo(copy.bodyPublisher().get());

        assertThat(expectContinue).isEqualTo(copy.expectContinue());

        assertThat(timeout).isEqualTo(copy.timeout().get());

        assertThat(version).isEqualTo(copy.version().get());
    }
}