package fr.monbanquet.sylph;

import fr.monbanquet.sylph.logger.DefaultRequestLogger;
import fr.monbanquet.sylph.logger.DefaultResponseLogger;
import fr.monbanquet.sylph.logger.RequestLogger;
import fr.monbanquet.sylph.logger.ResponseLogger;
import fr.monbanquet.sylph.parser.DefaultParser;
import fr.monbanquet.sylph.parser.Parser;
import fr.monbanquet.sylph.processor.DefaultResponseProcessor;
import fr.monbanquet.sylph.processor.ResponseProcessor;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

public class SylphHttpClientTestCopy {

    @Test
    void copy() throws NoSuchAlgorithmException {
        // given
        CookieHandler cookieHandler = new CookieHandler() {
            @Override
            public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
                return null;
            }

            @Override
            public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {

            }
        };
        Duration connectTimeout = Duration.ofSeconds(789);
        SSLContext sslContext = SSLContext.getDefault();
        int maximumPacketSize = 753;
        SSLParameters sslParameters = new SSLParameters();
        sslParameters.setWantClientAuth(true);
        sslParameters.setUseCipherSuitesOrder(true);
        sslParameters.setMaximumPacketSize(maximumPacketSize);
        Executor executor = command -> {
        };
        HttpClient.Redirect followRedirects = HttpClient.Redirect.ALWAYS;
        HttpClient.Version version = HttpClient.Version.HTTP_2;
        ProxySelector proxy = ProxySelector.getDefault();
        Authenticator authenticator = new Authenticator() {
        };

        SylphHttpRequestBuilder baseRequestBuilder = SylphHttpRequest.newBuilder();
        Parser parser = DefaultParser.create();
        RequestLogger requestLogger = DefaultRequestLogger.create();
        ResponseLogger responseLogger = DefaultResponseLogger.create();
        ResponseProcessor responseProcessor = DefaultResponseProcessor.create();

        SylphHttpClientBuilder clientBuilder = SylphHttpClient.newBuilder()
                .cookieHandler(cookieHandler)
                .connectTimeout(connectTimeout)
                .sslContext(sslContext)
                .sslParameters(sslParameters)
                .executor(executor)
                .followRedirects(followRedirects)
                .version(version)
                .proxy(proxy)
                .authenticator(authenticator);

        clientBuilder.baseRequestBuilder(baseRequestBuilder);
        clientBuilder.parser(parser);
        clientBuilder.requestLogger(requestLogger);
        clientBuilder.responseLogger(responseLogger);
        clientBuilder.responseProcessor(responseProcessor);

        SylphHttpClient client = clientBuilder.build();

        // when
        SylphHttpClient copy = client.copy();

        // then
        assertThat(copy.cookieHandler().get()).isEqualTo(cookieHandler);
        assertThat(copy.connectTimeout().get()).isEqualTo(connectTimeout);
        assertThat(copy.sslContext()).isEqualTo(sslContext);
        assertThat(copy.sslParameters().getWantClientAuth()).isTrue();
        assertThat(copy.sslParameters().getUseCipherSuitesOrder()).isTrue();
        assertThat(copy.sslParameters().getMaximumPacketSize()).isEqualTo(maximumPacketSize);
        assertThat(copy.executor().get()).isEqualTo(executor);
        assertThat(copy.followRedirects()).isEqualTo(followRedirects);
        assertThat(copy.version()).isEqualTo(version);
        assertThat(copy.proxy().get()).isEqualTo(proxy);
        assertThat(copy.authenticator().get()).isEqualTo(authenticator);
    }

}
