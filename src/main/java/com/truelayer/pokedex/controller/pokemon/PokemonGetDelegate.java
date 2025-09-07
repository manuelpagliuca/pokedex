package com.truelayer.pokedex.controller.pokemon;

import com.truelayer.pokedex.service.PokemonService;
import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import com.truelayer.pokedex.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PokemonGetDelegate {
    private final PokemonService pokemonService;
    private final TranslationService translationService;

    @Autowired
    public PokemonGetDelegate(PokemonService pokemonService, TranslationService translationService) {
        this.pokemonService = pokemonService;
        this.translationService = translationService;
    }

    public PokemonResponse getPokemonInfo(String name) {
        return pokemonService.getInfo(name);
    }

    public PokemonResponse getTranslatedPokemonInfo(String name) {
        PokemonResponse pokemonResponse = pokemonService.getInfo(name);

        String translatedDescription = translationService.translate(
                pokemonResponse.getDescription(),
                pokemonResponse.getHabitat(),
                pokemonResponse.isLegendary()
        );

        pokemonResponse.setDescription(translatedDescription);

        return pokemonResponse;
    }

}
