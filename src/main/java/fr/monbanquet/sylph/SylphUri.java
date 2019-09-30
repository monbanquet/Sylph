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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SylphUri {

    private String scheme;

    private String host;

    private Integer port;

    private String path;

    private Map<String, Object> queryParams = new HashMap<>();

    public static SylphUri newUri() {
        return new SylphUri();
    }

    public SylphUri scheme(String scheme) {
        Objects.requireNonNull(scheme, "Schema should not be null");
        this.scheme = scheme;
        return this;
    }

    public SylphUri https() {
        return scheme("https");
    }

    public SylphUri host(String host) {
        Objects.requireNonNull(host, "Host should not be null");
        this.host = host;
        return this;
    }

    public SylphUri port(int port) {
        this.port = port;
        return this;
    }

    public SylphUri path(String path) {
        Objects.requireNonNull(host, "Path should not be null");
        this.path = path;
        return this;
    }

    public SylphUri queryParams(String name, Object value) {
        Objects.requireNonNull(name, "Query param name should not be null");
        queryParams.put(name, value);
        return this;
    }

    public SylphUri queryParams(Map<String, Object> queryParams) {
        if (Objects.nonNull(queryParams)) {
            queryParams.forEach((k, v) -> queryParams(k, v));
        }
        return this;
    }

    public URI toUri() {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(scheme)) {
            scheme = "http";
        }
        sb.append(scheme);
        sb.append("://");
        if (Objects.isNull(host)) {
            throw new SylphException("Host is mandatory");
        }
        sb.append(host);
        if (Objects.nonNull(port)) {
            if (Objects.isNull(port)) {
                port = 80;
            }
            sb.append(":");
            sb.append(port);
        }
        if (Objects.nonNull(path)) {
            if (!path.startsWith("/")) {
                sb.append("/");
            }
            sb.append(path);
        }
        if (!queryParams.isEmpty()) {
            try {
                boolean firstParam = true;
                for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                    String name = entry.getKey();
                    Object value = entry.getValue();
                    if (firstParam) {
                        sb.append("?");
                        firstParam = false;
                    } else {
                        sb.append("&");
                    }
                    sb.append(URLEncoder.encode(name, "UTF-8"));
                    if (Objects.nonNull(value)) {
                        sb.append("=").append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new SylphException(e);
            }
        }
        return URI.create(sb.toString());
    }

}
