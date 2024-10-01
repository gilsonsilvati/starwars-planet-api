package br.com.starwars.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import static br.com.starwars.commons.PlanetConstants.EMPTY_PLANET;
import static br.com.starwars.commons.PlanetConstants.INVALID_PLANET;
import static br.com.starwars.commons.PlanetConstants.PLANET;
import static br.com.starwars.commons.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PlanetRepositoryTest {

    @Autowired
    PlanetRepository repository;

    @Autowired
    TestEntityManager entityManager;

    static final Integer ID = 1;

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
    @DisplayName("Create Planet with invalid data throw Exception")
    void createPlanet_WithInvalidData_ThrowException() {
        var emptyPlanet = EMPTY_PLANET;
        var invalidPlanet = INVALID_PLANET;

        assertThatThrownBy(() -> repository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Create Planet with existing name return Exception")
    void createPlanet_WithExistingName_ReturnException() {
        var savedPlanet = entityManager.persistFlushFind(PLANET);
        entityManager.detach(savedPlanet);
        savedPlanet.setId(null);

        assertThatThrownBy(() -> repository.save(savedPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Get Planet with by existing id return Planet")
    void getPlanet_WithByExistingId_ReturnPlanet() {
        var savedPlanet = entityManager.persistFlushFind(PLANET);
        var sut = repository.findById(savedPlanet.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(savedPlanet);
    }

    @Test
    @DisplayName("Get Planet with by unexisting id return Empty")
    void getPlanet_WithByUnexistingId_ReturnEmpty() {
        var sut = repository.findById(ID);

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Get Planet with by existing name return Planet")
    void getPlanet_WithByExistingName_ReturnPlanet() {
        var savedPlanet = entityManager.persistFlushFind(PLANET);
        var sut = repository.findByName(savedPlanet.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(savedPlanet);
    }

    @Test
    @DisplayName("Get Planet with by unexisting name return Empty")
    void getPlanet_WithByUnexistingName_ReturnEmpty() {
        var sut = repository.findByName(PLANET.getName());

        assertThat(sut).isEmpty();
    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    @DisplayName("List Planets returns filtered Planets")
    void listPlanets_ReturnsFilteredPlanets() throws Exception {
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(TATOOINE.getTerrain(), TATOOINE.getClimate()));

        var sutWithoutFilters = repository.findAll(queryWithoutFilters);
        var sutWithFilters = repository.findAll(queryWithFilters);

        assertThat(sutWithoutFilters).isNotEmpty();
        assertThat(sutWithoutFilters).hasSize(3);

        assertThat(sutWithFilters).isNotEmpty();
        assertThat(sutWithFilters).hasSize(1);
        assertThat(sutWithFilters.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    @DisplayName("List Planets return Empty")
    void listPlanets_ReturnEmpty() throws Exception {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        var sut = repository.findAll(query);

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Remove Planet with existing id, removes Planet from Database")
    void removePlanet_WithExistingId_RemovesPlanetFromDatabase() {
        var savedPlanet = entityManager.persistFlushFind(PLANET);

        repository.deleteById(savedPlanet.getId());

        var sut = entityManager.find(Planet.class, savedPlanet.getId());

        assertThat(sut).isNull();
    }

    @AfterEach
    void tearDown() {
        PLANET.setId(null);
    }
}
