package com.truelayer.pokedex.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TranslationApiClient {
    private static final Logger logger = LoggerFactory.getLogger(TranslationApiClient.class);
    private static final String YODA_URL = "https://api.funtranslations.com/translate/yoda";
    private static final String SHAKESPEARE_URL = "https://api.funtranslations.com/translate/shakespeare";
    private static final String CONTENTS_KEY = "contents";
    private static final String TRANSLATED_KEY = "translated";

    private final RestTemplate restTemplate;

    public TranslationApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String translateYoda(String text) {
        return callTranslationApi(YODA_URL, text);
    }

    public String translateShakespeare(String text) {
        return callTranslationApi(SHAKESPEARE_URL, text);
    }

    private String callTranslationApi(String url, String text) {
        try {
            HttpHeaders headers = getHttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("text=" + text, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey(CONTENTS_KEY)) {
                Map<String, String> contents = (Map<String, String>) body.get(CONTENTS_KEY);
                return contents.getOrDefault(TRANSLATED_KEY, text);
            }
            return text;
        } catch (Exception e) {
            logger.error("Error while calling translation API [{}]: {}", url, e.getMessage(), e);
            return text;
        }
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }
}
