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
package fr.monbanquet.sylph.exception;

import java.io.Serializable;
import java.text.MessageFormat;

public class SylphHttpResponseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String requestUri;
    private final String requestMethod;
    private final int responseCode;
    private final String errorBody;

    public SylphHttpResponseException(String requestUri, String requestMethod, int responseCode, String errorBody) {
        this.requestUri = requestUri;
        this.requestMethod = requestMethod;
        this.responseCode = responseCode;
        this.errorBody = errorBody;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("Error while executing call {0};{1} : responseCode={2}, errorBody={3}",
                requestMethod, requestUri, responseCode, errorBody);
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorBody() {
        return errorBody;
    }
}
