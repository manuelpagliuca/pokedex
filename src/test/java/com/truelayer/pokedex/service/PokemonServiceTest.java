package com.truelayer.pokedex.service;

import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService pokemonService;

    @Test
    void shouldReturnPokemonResponse_whenApiReturnsValidData() {
        // given
        String pokemonName = "mewtwo";
        String url = "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName;

        PokemonSpeciesResponse response = new PokemonSpeciesResponse();
        response.setLegendary(true);

        PokemonSpeciesResponse.Habitat habitat = new PokemonSpeciesResponse.Habitat();
        habitat.setName("rare");
        response.setHabitat(habitat);

        PokemonSpeciesResponse.Language lang = new PokemonSpeciesResponse.Language();
        lang.setName("en");

        PokemonSpeciesResponse.FlavorTextEntry entry = new PokemonSpeciesResponse.FlavorTextEntry();
        entry.setFlavorText("Created by a scientist...");
        entry.setLanguage(lang);

        response.setFlavorTextEntries(List.of(entry));

        when(restTemplate.getForObject(url, PokemonSpeciesResponse.class)).thenReturn(response);

        // when
        PokemonResponse result = pokemonService.getInfo(pokemonName);

        // then
        assertEquals("mewtwo", result.getName());
        assertEquals("created by a scientist...", result.getDescription().toLowerCase());
        assertEquals("rare", result.getHabitat());
        assertTrue(result.isLegendary());
    }
}
