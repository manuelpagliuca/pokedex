package com.truelayer.pokedex.service;

import com.truelayer.pokedex.client.TranslationApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    private TranslationApiClient translationApiClient;

    @InjectMocks
    private TranslationService translationService;

    @Test
    void shouldUseYodaTranslation_forLegendaryPokemon() {
        // given
        String original = "Test description";
        when(translationApiClient.translateYoda(original)).thenReturn("Yoda style");

        // when
        String result = translationService.translate(original, "forest", true);

        // then
        assertEquals("Yoda style", result);
    }

    @Test
    void shouldUseShakespeareTranslation_forNormalPokemon() {
        // given
        String original = "Test description";
        when(translationApiClient.translateShakespeare(original)).thenReturn("Shakespeare style");

        // when
        String result = translationService.translate(original, "forest", false);

        // then
        assertEquals("Shakespeare style", result);
    }
}
