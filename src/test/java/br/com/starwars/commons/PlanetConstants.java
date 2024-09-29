package br.com.starwars.commons;

import br.com.starwars.domain.Planet;
import br.com.starwars.web.PlanetRequest;
import br.com.starwars.web.PlanetResponse;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("Tatooine", "desert", "arid");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Planet EMPTY_PLANET = new Planet();

    public static final PlanetRequest PLANET_REQUEST = new PlanetRequest("Tatooine", "desert", "arid");
    public static final PlanetRequest INVALID_PLANET_REQUEST = new PlanetRequest("", "", "");

    public static final PlanetResponse PLANET_RESPONSE = new PlanetResponse("Tatooine", "desert", "arid");
}
