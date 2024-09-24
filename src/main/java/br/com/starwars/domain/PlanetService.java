package br.com.starwars.domain;

import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private final PlanetRepository repository;

    public PlanetService(PlanetRepository repository) {
        this.repository = repository;
    }

    public Planet create(Planet planet) {

        return repository.save(planet);
    }
}
