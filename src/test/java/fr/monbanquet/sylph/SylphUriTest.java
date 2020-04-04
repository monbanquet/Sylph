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

import fr.monbanquet.sylph.exception.SylphException;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SylphUriTest {

    @Test
    void toUri_all_methods() {
        URI uri = SylphUri.newUri()
                .scheme("https")
                .host("my.server.fake")
                .port(443)
                .path("/posts")
                .queryParams("userId", 1)
                .queryParams("postId", 2)
                .toUri();

        assertEquals(uri.toString(), "https://my.server.fake:443/posts?postId=2&userId=1");
    }

    @Test
    void toUri_minimal() {
        URI uri = SylphUri.newUri()
                .host("my.server.fake")
                .toUri();

        assertEquals(uri.toString(), "http://my.server.fake");
    }

    @Test
    void toUri_with_scheme() {
        URI uri = SylphUri.newUri()
                .https()
                .host("my.server.fake")
                .toUri();

        assertEquals(uri.toString(), "https://my.server.fake");
    }

    @Test
    void toUri_with_port() {
        URI uri = SylphUri.newUri()
                .host("my.server.fake")
                .port(555)
                .toUri();

        assertEquals(uri.toString(), "http://my.server.fake:555");
    }

    @Test
    void toUri_with_query_params() {
        URI uri = SylphUri.newUri()
                .host("my.server.fake")
                .queryParams("userId", 1)
                .queryParams("postId", 2)
                .toUri();

        assertEquals(uri.toString(), "http://my.server.fake?postId=2&userId=1");
    }

    @Test
    void toUri_with_query_params_map() {
        URI uri = SylphUri.newUri()
                .host("my.server.fake")
                .queryParams(Map.of("postId", 2, "userId", 1))
                .toUri();

        assertEquals(uri.toString(), "http://my.server.fake?postId=2&userId=1");
    }

    @Test
    void toUri_should_add_slash_to_path() {
        URI uri = SylphUri.newUri()
                .host("my.server.fake")
                .path("posts")
                .toUri();

        assertEquals(uri.toString(), "http://my.server.fake/posts");
    }

    @Test
    void toUri_should_throw_exception_when_no_host() {
        assertThrows(SylphException.class, () -> SylphUri.newUri().toUri());
    }

}