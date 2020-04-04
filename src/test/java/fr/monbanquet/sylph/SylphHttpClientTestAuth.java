package fr.monbanquet.sylph;

import fr.monbanquet.sylph.exception.SylphHttpResponseException;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SylphHttpClientTestAuth {

    private static final String URL = "https://postman-echo.com/basic-auth";

    @Test
    void base64() {
        // given
        String headerContentType = "content-type";
        String headerContactTypeValue = "application/json; charset=utf-8";
        String username = "postman";
        String password = "password";
        String responseBody = "{\"authenticated\":true}";


        SylphHttpClient client = Sylph.builder()
                .setBaseRequest(SylphHttpRequestBuilder
                        .newBuilder()
                        .authBase64(username, password))
                .getClient();

        // when
        SylphHttpResponse<String, String> response = client
                .GET(URL)
                .send(String.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.headers().allValues(headerContentType)).contains(headerContactTypeValue);
        assertThat(response.body()).contains(responseBody);
    }

    @Test
    void base64_error() {
        // given
        int codeError = 401;
        String bodyError = "Unauthorized";
        SylphHttpClient client = Sylph.builder()
                .getClient();

        // when
        AbstractThrowableAssert<?, ? extends Throwable> catchError = assertThatThrownBy(() -> client.GET(URL).send(String.class));

        // then
        catchError.isInstanceOf(SylphHttpResponseException.class)
                .hasMessageContainingAll(String.valueOf(codeError), bodyError)
                .hasFieldOrPropertyWithValue("responseCode", codeError)
                .hasFieldOrPropertyWithValue("errorBody", bodyError);
    }

}