package com.truelayer.pokedex.controller;

import com.truelayer.pokedex.controller.pokemon.PokemonController;
import com.truelayer.pokedex.controller.pokemon.PokemonGetDelegate;
import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonGetDelegate delegate;

    @Test
    void shouldReturnPokemonInfo() throws Exception {
        PokemonResponse response = new PokemonResponse("mewtwo", "desc", "rare", true);
        when(delegate.getPokemonInfo("mewtwo")).thenReturn(response);

        mockMvc.perform(get("/pokemon/mewtwo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mewtwo"))
                .andExpect(jsonPath("$.habitat").value("rare"))
                .andExpect(jsonPath("$.isLegendary").value(true));
    }
}
