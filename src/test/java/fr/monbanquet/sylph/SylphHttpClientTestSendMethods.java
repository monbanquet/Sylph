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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {1080})
public class SylphHttpClientTestSendMethods extends SylphHttpClientTestSendMethodsAbstract {

    public SylphHttpClientTestSendMethods(ClientAndServer client) {
        super(client);
    }

    @Override
    protected SylphHttpResponse<String, String> call_send_with_request_param_and_responseBodyHandler(HttpRequest request, SylphHttpClient client) {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    protected SylphHttpResponse<String, String> call_send_with_responseBodyHandler(SylphHttpClient client) {
        return client.send(HttpResponse.BodyHandlers.ofString());
    }

    @Override
    protected SylphHttpResponse<String, Todo> call_send_with_request_param_and_returnType_param(HttpRequest request, SylphHttpClient client) {
        return client.send(request, Todo.class);
    }

    @Override
    protected Todo call_send_with_returnType_object() {
        return Sylph.newClient().GET(URL).send(Todo.class).asObject();
    }

    @Override
    protected List<Todo> call_send_with_returnType_list() {
        return Sylph.newClient().GET(URL).send(Todo.class).asList();
    }

    @Override
    protected Todo call_body_with_returnType() {
        return Sylph.newClient().GET(URL).body(Todo.class);
    }

    @Override
    protected List<Todo> call_bodyList_with_returnType() {
        return Sylph.newClient().GET(URL).bodyList(Todo.class);
    }

    @Override
    protected void call_send_with_wrong_uri_scheme_should_throws_error() {
        Sylph.newClient().GET("NOT_EXISTS").send();
    }

    @Override
    protected void call_send_with_uri_not_exists_should_throws_error() {
        Sylph.newClient().GET("http://not.exists").send();
    }

}

