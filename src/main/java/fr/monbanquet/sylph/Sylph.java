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

import fr.monbanquet.sylph.logger.DefaultResponseLogger;
import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.DefaultResponseProcessor;
import fr.monbanquet.sylph.processor.ResponseProcessor;

import java.util.Objects;

public class Sylph {

    private SylphHttpRequestBuilder baseRequest;
    private Parser parser;
    private ResponseLogger responseLogger;
    private ResponseProcessor responseProcessor;
    private SylphHttpClientBuilder client = new SylphHttpClientBuilder();

    public static Sylph builder() {
        return new Sylph();
    }

    public static SylphHttpClient newClient() {
        return new Sylph().getClient();
    }

    public SylphHttpClient getClient() {
        return this.doBuild();
    }

    private SylphHttpClient doBuild() {

        if (Objects.isNull(baseRequest)) {
            baseRequest = SylphHttpRequestBuilder.newBuilder();
        }

        if (Objects.isNull(parser)) {
            parser = DefaultParser.create();
        }

        if (Objects.isNull(responseLogger)) {
            responseLogger = DefaultResponseLogger.create();
        }

        if (Objects.isNull(responseProcessor)) {
            responseProcessor = DefaultResponseProcessor.create();
        }

        baseRequest.parser(parser);

        client.baseRequest(baseRequest);
        client.parser(parser);
        client.responseLogger(responseLogger);
        client.responseProcessor(responseProcessor);

        return client.build();
    }

    public Sylph setBaseRequest(SylphHttpRequestBuilder baseRequest) {
        this.baseRequest = baseRequest;
        return this;

    }

    public Sylph setClient(SylphHttpClientBuilder clientBuilder) {
        this.client = clientBuilder;
        return this;
    }

    public Sylph setParser(Parser parser) {
        this.parser = parser;
        return this;
    }

    public Sylph setResponseLogger(ResponseLogger responseLogger) {
        this.responseLogger = responseLogger;
        return this;
    }

    public Sylph setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
        return this;
    }

}