package com.truelayer.pokedex.model.pokemon.enums;

public enum PokemonHabitat {
    CAVE, FOREST, RARE, SEA, UNKNOWN;

    public static PokemonHabitat fromString(String name) {
        try {
            return PokemonHabitat.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}

