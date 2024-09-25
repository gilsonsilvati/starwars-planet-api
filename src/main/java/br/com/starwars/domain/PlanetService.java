package br.com.starwars.domain;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    private final PlanetRepository repository;

    public PlanetService(PlanetRepository repository) {
        this.repository = repository;
    }

    public Planet create(Planet planet) {

        return repository.save(planet);
    }

    public Optional<Planet> getById(Integer id) {

        return repository.findById(id);
    }

    public Optional<Planet> getByName(String name) {

        return repository.findByName(name);
    }

    public List<Planet> list(String terrain, String climate) {

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(terrain, climate));

        return repository.findAll(query);
    }

    public void remove(Integer id) {

        repository.deleteById(id);
    }
}
