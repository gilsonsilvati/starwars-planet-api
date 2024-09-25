package br.com.starwars.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

    @Mock
    PlanetRepository repository;

    @InjectMocks
    PlanetService service;

    @Test
    @DisplayName("Create Planet with valid data return Planet")
    void createPlanet_WithValidData_ReturnPlanet() {
        when(repository.save(PLANET)).thenReturn(PLANET);

        // sut: system under test (sistema em teste)
        var sut = service.create(PLANET);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PLANET);
    }

}
