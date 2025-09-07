package com.truelayer.pokedex.client;

import com.truelayer.pokedex.model.pokemon_species.PokemonSpeciesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class PokemonApiClientTest {

    private PokemonApiClient apiClient;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        apiClient = new PokemonApiClient(restTemplate);
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getPokemonSpecie_shouldReturnPokemonSpeciesResponse() {
        // Arrange
        String pokemonName = "pikachu";
        String jsonResponse = """
                {
                  "name": "pikachu",
                  "is_legendary": false
                }
                """;

        mockServer.expect(requestTo("https://pokeapi.co/api/v2/pokemon-species/" + pokemonName))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        // Act
        PokemonSpeciesResponse response = apiClient.getPokemonSpecies(pokemonName);

        // Assert
        assertNotNull(response);
        assertEquals("pikachu", response.getName());
        assertFalse(response.isLegendary());

        mockServer.verify();
    }
}
