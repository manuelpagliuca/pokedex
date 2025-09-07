package com.truelayer.pokedex.service;

import com.truelayer.pokedex.client.PokemonApiClient;
import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import com.truelayer.pokedex.model.pokemon.enums.PokemonHabitat;
import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    private PokemonApiClient apiClient;
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        apiClient = Mockito.mock(PokemonApiClient.class);
        pokemonService = new PokemonService(apiClient);
    }

    @Test
    void getInfo_shouldReturnCorrectPokemonResponse() {
        // Arrange
        PokemonSpeciesResponse.FlavorTextEntry flavorTextEntry = new PokemonSpeciesResponse.FlavorTextEntry();
        flavorTextEntry.setLanguage(new PokemonSpeciesResponse.Language("en"));
        flavorTextEntry.setFlavorText("Test description");

        PokemonSpeciesResponse.Habitat habitat = new PokemonSpeciesResponse.Habitat();
        habitat.setName("cave");

        PokemonSpeciesResponse speciesResponse = new PokemonSpeciesResponse();
        speciesResponse.setFlavorTextEntries(List.of(flavorTextEntry));
        speciesResponse.setHabitat(habitat);
        speciesResponse.setLegendary(true);

        when(apiClient.getPokemonSpecies("zubat")).thenReturn(speciesResponse);

        // Act
        PokemonResponse response = pokemonService.getInfo("zubat");

        // Assert
        assertEquals("zubat", response.getName());
        assertEquals("Test description", response.getDescription());
        assertEquals(PokemonHabitat.CAVE.name().toLowerCase(), response.getHabitat());
        assertTrue(response.isLegendary());

        verify(apiClient, times(1)).getPokemonSpecies("zubat");
    }

    @Test
    void getInfo_shouldThrowException_whenNoDataFound() {
        // Arrange
        when(apiClient.getPokemonSpecies("unknown")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pokemonService.getInfo("unknown");
        });

        assertEquals("No data found for unknown", exception.getMessage());
    }
}
