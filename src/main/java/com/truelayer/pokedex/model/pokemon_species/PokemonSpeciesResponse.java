package com.truelayer.pokedex.model.pokemon_species;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonSpeciesResponse {
    private String name;
    private Habitat habitat;
    @JsonProperty("is_legendary")
    private boolean isLegendary;
    @JsonProperty("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    public static class Habitat {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class FlavorTextEntry {
        @JsonProperty("flavor_text")
        private String flavorText;
        private Language language;

        public String getFlavorText() {
            return flavorText;
        }

        public void setFlavorText(String flavorText) {
            this.flavorText = flavorText;
        }

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }
    }

    public static class Language {
        private String name;

        public Language(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }
}
