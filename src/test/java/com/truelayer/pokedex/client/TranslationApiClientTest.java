package com.truelayer.pokedex.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class TranslationApiClientTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TranslationApiClient client;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnTranslatedText() {
        String responseJson = """
            {
              "contents": {
                "translated": "Yoda test",
                "text": "original",
                "translation": "yoda"
              }
            }
            """;

        mockServer.expect(requestTo("https://api.funtranslations.com/translate/yoda"))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        String result = client.translateYoda("original");

        assertEquals("Yoda test", result);
    }
}
