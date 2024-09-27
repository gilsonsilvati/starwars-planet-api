package br.com.starwars.common;

import br.com.starwars.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("Tatooine", "desert", "arid");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Planet EMPTY_PLANET = new Planet();
}
