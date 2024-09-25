package br.com.starwars.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PlanetService.class)
class PlanetServiceTest {

    @MockBean
    PlanetRepository repository;

    @Autowired
    PlanetService service;

    @Test
    @DisplayName("Create Planet with valid data return Planet")
    void createPlanet_WithValidData_ReturnPlanet() {
        // AAA
        // Arrange
        when(repository.save(PLANET)).thenReturn(PLANET);

        // Act
        // sut: system under test (sistema em teste)
        var sut = service.create(PLANET);

        // Assert
        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PLANET);
    }

}
