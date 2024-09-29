package br.com.starwars.web;

import br.com.starwars.domain.Planet;
import jakarta.validation.constraints.NotEmpty;

public record PlanetRequest(
        @NotEmpty String name,
        @NotEmpty String terrain,
        @NotEmpty String climate) {

    public Planet toPlanet() {

        return new Planet(name, terrain, climate);
    }
}
