package com.truelayer.pokedex.service;

import com.truelayer.pokedex.client.PokemonApiClient;
import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import com.truelayer.pokedex.model.pokemon.enums.PokemonHabitat;
import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {
    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);
    private static final String LANGUAGE = "en";
    private final PokemonApiClient pokemonApiClient;

    public PokemonService(PokemonApiClient pokemonApiClient) {
        this.pokemonApiClient = pokemonApiClient;
    }

    public PokemonResponse getInfo(String pokemonName) {
        PokemonSpeciesResponse pokemonSpeciesResponse = pokemonApiClient.getPokemonSpecies(pokemonName);

        if (pokemonSpeciesResponse == null) {
            logger.error("No data found for Pokémon: {}", pokemonName);
            throw new RuntimeException("No data found for " + pokemonName);
        }

        String description = extractDescription(pokemonSpeciesResponse);
        PokemonHabitat habitat = extractHabitat(pokemonSpeciesResponse);
        boolean isLegendary = pokemonSpeciesResponse.isLegendary();

        logger.info("Fetched Pokémon [{}] → habitat={}, legendary={}", pokemonName, habitat, isLegendary);

        return new PokemonResponse(pokemonName, description, habitat.name().toLowerCase(), isLegendary);
    }

    private String extractDescription(PokemonSpeciesResponse response) {
        return response.getFlavorTextEntries().stream()
                .filter(entry -> LANGUAGE.equals(entry.getLanguage().getName()))
                .map(entry -> entry.getFlavorText()
                        .replace("\n", " ")
                        .replace("\f", " "))
                .findFirst()
                .orElseGet(() -> {
                    logger.warn("No English description found for Pokémon species response");
                    return "No description found";
                });
    }

    private PokemonHabitat extractHabitat(PokemonSpeciesResponse response) {
        if (response.getHabitat() == null) {
            logger.warn("No habitat found for Pokémon species response");
            return PokemonHabitat.UNKNOWN;
        }
        return PokemonHabitat.fromString(response.getHabitat().getName());
    }
}
