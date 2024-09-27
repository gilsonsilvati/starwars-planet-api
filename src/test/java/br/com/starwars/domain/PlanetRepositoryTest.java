package br.com.starwars.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static br.com.starwars.common.PlanetConstants.EMPTY_PLANET;
import static br.com.starwars.common.PlanetConstants.INVALID_PLANET;
import static br.com.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PlanetRepositoryTest {

    @Autowired
    PlanetRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Create Planet with valid data return Planet")
    void createPlanet_WithValidData_ReturnPlanet() {
        var savedPlanet = repository.save(PLANET);

        // sut: system under test (sistema em teste)
        var sut = entityManager.find(Planet.class, savedPlanet.getId());

        assertThat(sut).isNotNull();

        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    @DisplayName("Create Planet with invalid data throw exception")
    void createPlanet_WithInvalidData_ThrowException() {
        Planet emptyPlanet = EMPTY_PLANET;
        Planet invalidPlanet = INVALID_PLANET;

        assertThatThrownBy(() -> repository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }
}
