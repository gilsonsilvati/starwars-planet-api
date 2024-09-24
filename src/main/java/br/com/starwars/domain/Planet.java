package br.com.starwars.domain;

import br.com.starwars.web.PlanetResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String terrain;
    private String climate;

    @Deprecated
    public Planet() { }

    public Planet(String name, String terrain, String climate) {
        this.name = name;
        this.terrain = terrain;
        this.climate = climate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getClimate() {
        return climate;
    }

    public PlanetResponse toPlanetResponse() {

        return new PlanetResponse(name, terrain, climate);
    }
}
