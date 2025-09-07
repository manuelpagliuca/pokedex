package com.truelayer.pokedex.client;

import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PokemonApiClient {
    private static final Logger logger = LoggerFactory.getLogger(PokemonApiClient.class);
    private static final String POKEMON_SPECIES_URI = "https://pokeapi.co/api/v2/pokemon-species/";
    private final RestTemplate restTemplate;

    public PokemonApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PokemonSpeciesResponse getPokemonSpecies(String pokemonName) {
        String url = composePokemonSpeciesUrl(pokemonName);
        logger.debug("Fetching Pokémon info from URL: {}", url);
        return restTemplate.getForObject(url, PokemonSpeciesResponse.class);
    }

    private static String composePokemonSpeciesUrl(String name) {
        return UriComponentsBuilder.fromUriString(POKEMON_SPECIES_URI + name).toUriString();
    }
}
