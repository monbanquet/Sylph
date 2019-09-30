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

import fr.monbanquet.sylph.parser.Parser;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class SylphHttpResponseTransform<T> extends SylphHttpResponse<String, T> {

    private final HttpResponse<String> response;
    private final Class<T> bodyType;
    private final Parser parser;

    public SylphHttpResponseTransform(HttpResponse<String> response, Class<T> bodyType, Parser parser) {
        super(response);
        this.response = response;
        this.bodyType = bodyType;
        this.parser = parser;
    }

    public T asObject() {
        Objects.requireNonNull(parser);
        String data = response.body();
        return parser.deserialize(data, bodyType);
    }

    public List<T> asList() {
        Objects.requireNonNull(parser);
        String data = response.body();
        return parser.deserializeList(data, bodyType);
    }

}
