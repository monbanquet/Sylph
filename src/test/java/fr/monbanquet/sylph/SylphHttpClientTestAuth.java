package fr.monbanquet.sylph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

class SylphHttpClientTestAuth {

    private static final String URL = "https://postman-echo.com/basic-auth";
    private static final String username = "postman";
    private static final String password = "password";

    @Test
    void base64() {
        // given
        SylphHttpClient client = Sylph.builder()
                .setClient(
                        SylphHttpClient.newBuilder()
                                .authBase64(username, password))
                .getClient();

        // when
        SylphHttpResponse<String, String> response = client
                .GET(URI.create(URL))
                .send(String.class);

        // then
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(List.of("application/json; charset=utf-8"), response.headers().allValues("content-type"));
        Assertions.assertTrue(response.body().contains("{\"authenticated\":true}"));
    }


}