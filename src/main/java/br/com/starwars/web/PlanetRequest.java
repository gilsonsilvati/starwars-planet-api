package br.com.starwars.web;

import br.com.starwars.domain.Planet;

public record PlanetRequest(String name, String terrain, String climate) {

    public Planet toPlanet() {

        return new Planet(name, terrain, climate);
    }
}
