package br.com.starwars.web;

import br.com.starwars.domain.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/planets")
public class PlanetController {

    private final PlanetService service;

    public PlanetController(PlanetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PlanetResponse> create(@RequestBody PlanetRequest request) {
        var planet = service.create(request.toPlanet());

        return ResponseEntity.status(HttpStatus.CREATED).body(planet.toPlanetResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponse> getById(@PathVariable Integer id) {
        return service.getById(id).map(planet -> ResponseEntity.ok(planet.toPlanetResponse()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public ResponseEntity<PlanetResponse> getByName(@PathVariable String name) {
        return service.getByName(name).map(planet -> ResponseEntity.ok(planet.toPlanetResponse()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
