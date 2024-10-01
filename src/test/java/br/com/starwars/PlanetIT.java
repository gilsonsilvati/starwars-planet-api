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

import static br.com.starwars.commons.PlanetConstants.PLANET_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PlanetIT {

    @Autowired
    TestRestTemplate restTemplate;

    static final String URL = "/api/v1/planets";

    @Test
    @DisplayName("Create Planet Return Created")
    void createPlanet_ReturnCreated() {
        var sut = restTemplate.postForEntity(URL, PLANET_REQUEST, PlanetResponse.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(sut.getBody()).isNotNull();

        assertThat(sut.getBody().name()).isEqualTo(PLANET_REQUEST.name());
        assertThat(sut.getBody().terrain()).isEqualTo(PLANET_REQUEST.terrain());
        assertThat(sut.getBody().climate()).isEqualTo(PLANET_REQUEST.climate());
    }
}
