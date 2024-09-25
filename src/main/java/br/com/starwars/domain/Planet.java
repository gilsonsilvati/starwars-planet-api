package br.com.starwars.domain;

import br.com.starwars.web.PlanetResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String terrain;
    private String climate;

    /**
     * @deprecated Use {@link #Planet(String, String, String)}
     */
    @Deprecated(since = "1.0.0")
    public Planet() { }

    public Planet(String name, String terrain, String climate) {
        this.name = name;
        this.terrain = terrain;
        this.climate = climate;
    }

    public Planet(String terrain, String climate) {
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

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(terrain)
                .append(climate)
                .toHashCode();
    }

    public PlanetResponse toPlanetResponse() {

        return new PlanetResponse(name, terrain, climate);
    }
}
