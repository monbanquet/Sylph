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

import fr.monbanquet.sylph.logger.DefaultRequestLogger;
import fr.monbanquet.sylph.logger.DefaultResponseLogger;
import fr.monbanquet.sylph.logger.RequestLogger;
import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.DefaultResponseProcessor;
import fr.monbanquet.sylph.processor.ResponseProcessor;

import java.util.Objects;

public class Sylph {

    private SylphHttpRequestBuilder internalBaseRequest;
    private Parser internalParser;
    private RequestLogger internalRequestLogger;
    private ResponseLogger internalResponseLogger;
    private ResponseProcessor internalResponseProcessor;
    private SylphHttpClientBuilder internalClient;

    public static Sylph builder() {
        return new Sylph();
    }

    public static SylphHttpClient newClient() {
        return new Sylph().getClient();
    }

    public SylphHttpClient getClient() {
        return this.build();
    }

    private SylphHttpClient build() {

        SylphHttpRequestBuilder baseRequest;
        Parser parser;
        RequestLogger requestLogger;
        ResponseLogger responseLogger;
        ResponseProcessor responseProcessor;
        SylphHttpClientBuilder client;

        baseRequest = (Objects.isNull(internalBaseRequest))
                ? SylphHttpRequestBuilder.newBuilder()
                : internalBaseRequest;

        parser = (Objects.isNull(internalParser))
                ? DefaultParser.create()
                : internalParser;

        requestLogger = (Objects.isNull(internalRequestLogger))
                ? DefaultRequestLogger.create()
                : internalRequestLogger;

        responseLogger = (Objects.isNull(internalResponseLogger))
                ? DefaultResponseLogger.create()
                : internalResponseLogger;

        responseProcessor = (Objects.isNull(internalResponseProcessor))
                ? DefaultResponseProcessor.create()
                : internalResponseProcessor;

        baseRequest.parser(parser);

        client = new SylphHttpClientBuilder();
        client.baseRequest(baseRequest);
        client.parser(parser);
        client.requestLogger(requestLogger);
        client.responseLogger(responseLogger);
        client.responseProcessor(responseProcessor);

        return client.build();
    }

    public Sylph setBaseRequest(SylphHttpRequestBuilder baseRequest) {
        this.internalBaseRequest = baseRequest;
        return this;

    }

    public Sylph setClient(SylphHttpClientBuilder clientBuilder) {
        this.internalClient = clientBuilder;
        return this;
    }

    public Sylph setParser(Parser parser) {
        this.internalParser = parser;
        return this;
    }

    public Sylph setRequestLogger(RequestLogger internalRequestLogger) {
        this.internalRequestLogger = internalRequestLogger;
        return this;
    }

    public Sylph setResponseLogger(ResponseLogger responseLogger) {
        this.internalResponseLogger = responseLogger;
        return this;
    }

    public Sylph setResponseProcessor(ResponseProcessor responseProcessor) {
        this.internalResponseProcessor = responseProcessor;
        return this;
    }

}