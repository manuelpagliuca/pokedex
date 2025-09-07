package com.truelayer.pokedex.service;

import com.truelayer.pokedex.client.TranslationApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.truelayer.pokedex.model.pokemon.enums.PokemonHabitat.CAVE;

@Service
public class TranslationService {
    private final static Logger logger = LoggerFactory.getLogger(TranslationService.class);
    private final TranslationApiClient translationApiClient;

    @Autowired
    TranslationService(TranslationApiClient translationApiClient) {
        this.translationApiClient = translationApiClient;
    }

    public String translate(String description, String habitat, boolean isLegendary) {
        try {
            if (CAVE.name().equalsIgnoreCase(habitat) || isLegendary) {
                return translationApiClient.translateYoda(description);
            } else {
                return translationApiClient.translateShakespeare(description);
            }
        } catch (Exception e) {
            logger.error("Error using translationApiClient, error: {}", e.getMessage());
            return description;
        }
    }
}
