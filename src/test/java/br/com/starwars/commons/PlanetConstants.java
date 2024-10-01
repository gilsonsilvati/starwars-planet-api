package br.com.starwars.commons;

import br.com.starwars.domain.Planet;
import br.com.starwars.web.PlanetRequest;
import br.com.starwars.web.PlanetResponse;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("Tatooine", "desert", "arid");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Planet EMPTY_PLANET = new Planet();

    public static final PlanetRequest PLANET_REQUEST = new PlanetRequest("Tatooine", "desert", "arid");
    public static final PlanetRequest PLANET_REQUEST_IT = new PlanetRequest("Tatooine2", "desert2", "arid2");
    public static final PlanetRequest INVALID_PLANET_REQUEST = new PlanetRequest("", "", "");

    public static final PlanetResponse PLANET_RESPONSE = new PlanetResponse("Tatooine", "desert", "arid");

    public static final Planet TATOOINE = new Planet(1, "Tatooine", "arid", "desert");
    public static final Planet ALDERAAN = new Planet(2, "Alderaan", "temperate", "grasslands, mountains");
    public static final Planet YAVINIV = new Planet(3, "Yavin IV", "temperate, tropical", "jungle, rainforests");

    public static final List<Planet> PLANETS = new ArrayList<>() {
        {
            add(TATOOINE);
            add(ALDERAAN);
            add(YAVINIV);
        }
    };
}
