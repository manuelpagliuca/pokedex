package com.truelayer.pokedex.controller.pokemon;

import com.truelayer.pokedex.model.pokemon.PokemonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonGetDelegate getDelegate;

    @Autowired
    public PokemonController(PokemonGetDelegate pokemonGetDelegate) {
        this.getDelegate = pokemonGetDelegate;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable String name) {
        return ResponseEntity.ok(getDelegate.getPokemonInfo(name));
    }

    @GetMapping("/translated/{name}")
    public ResponseEntity<PokemonResponse> getTranslatedPokemon(@PathVariable String name) {
        return ResponseEntity.ok(getDelegate.getTranslatedPokemonInfo(name));
    }
}
