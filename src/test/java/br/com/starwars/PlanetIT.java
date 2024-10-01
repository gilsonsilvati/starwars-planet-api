package br.com.starwars;

import br.com.starwars.web.PlanetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static br.com.starwars.commons.PlanetConstants.PLANET_REQUEST_IT;
import static br.com.starwars.commons.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/import_planets.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/truncate_planets.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class PlanetIT {

    @Autowired
    TestRestTemplate restTemplate;

    static final String URL = "/api/v1/planets";
    static final Integer ID = 1;
    static final String NAME = "Tatooine";

    @Test
    @DisplayName("Create Planet Return Created")
    void createPlanet_ReturnCreated() {
        var sut = restTemplate.postForEntity(URL, PLANET_REQUEST_IT, PlanetResponse.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody().name()).isEqualTo(PLANET_REQUEST_IT.name());
        assertThat(sut.getBody().terrain()).isEqualTo(PLANET_REQUEST_IT.terrain());
        assertThat(sut.getBody().climate()).isEqualTo(PLANET_REQUEST_IT.climate());
    }

    @Test
    @DisplayName("Find Planet By Id Return Ok")
    void findPlanetById_ReturnOk() {
        var sut = restTemplate.getForEntity(URL + "/" + ID, PlanetResponse.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody()).isEqualTo(TATOOINE.toPlanetResponse());
    }

    @Test
    @DisplayName("Find Planet By Name Return Ok")
    void findPlanetByName_ReturnOk() {
        var sut = restTemplate.getForEntity(URL + "/name/" + NAME, PlanetResponse.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody()).isEqualTo(TATOOINE.toPlanetResponse());
    }

    @Test
    @DisplayName("List Planets Return All Planets")
    void listPlanets_ReturnAllPlanets() {
        var sut = restTemplate.getForEntity(URL, PlanetResponse[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody()).hasSize(3);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE.toPlanetResponse());
    }

    @Test
    @DisplayName("List Planets By Climate Return Planets")
    void listPlanets_ByClimate_ReturnPlanets() {
        var sut = restTemplate.getForEntity(URL + "?climate=" + TATOOINE.getClimate(), PlanetResponse[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE.toPlanetResponse());
    }

    @Test
    @DisplayName("List Planets By Terrain Return Planets")
    void listPlanets_ByTerrain_ReturnPlanets() {
        var sut = restTemplate.getForEntity(URL + "?terrain=" + TATOOINE.getTerrain(), PlanetResponse[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE.toPlanetResponse());
    }

    @Test
    @DisplayName("Remove Planets Return No Content")
    void removePlanets_ReturnNoContent() {
        var sut = restTemplate.exchange(URL + "/" + ID, DELETE, null, Void.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
