package com.truelayer.pokedex.service;

import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import com.truelayer.pokedex.model.pokemon.enums.PokemonHabitat;
import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PokemonService {
    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    private final RestTemplate restTemplate;
    private static final String POKEMON_SPECIES_URI = "https://pokeapi.co/api/v2/pokemon-species/";
    private static final String LANGUAGE = "en";

    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PokemonResponse getInfo(String pokemonName) {
        PokemonSpeciesResponse pokemonSpeciesResponse = getPokemonSpeciesResponse(pokemonName);

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

    private PokemonSpeciesResponse getPokemonSpeciesResponse(String pokemonName) {
        String url = composeUrl(pokemonName);
        logger.debug("Fetching Pokémon info from URL: {}", url);
        return restTemplate.getForObject(url, PokemonSpeciesResponse.class);
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

    private static String composeUrl(String name) {
        return UriComponentsBuilder.fromUriString(POKEMON_SPECIES_URI + name).toUriString();
    }
}
