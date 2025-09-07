package com.truelayer.pokedex.model.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonResponse {
    private String name;
    private String description;
    private String habitat;
    private boolean isLegendary;

    public PokemonResponse(String name, String description, String habitatName, boolean isLegendary) {
        this.name = name;
        this.description = description;
        this.habitat = habitatName;
        this.isLegendary = isLegendary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    @JsonProperty("isLegendary")
    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        this.isLegendary = legendary;
    }

}