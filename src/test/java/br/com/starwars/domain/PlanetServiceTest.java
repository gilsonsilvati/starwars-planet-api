package br.com.starwars.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.starwars.common.PlanetConstants.INVALID_PLANET;
import static br.com.starwars.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

    @Mock
    PlanetRepository repository;

    @InjectMocks
    PlanetService service;

    static final Integer ID = 1;

    @Test
    @DisplayName("Create Planet with valid data return Planet")
    void createPlanet_WithValidData_ReturnPlanet() {
        when(repository.save(PLANET)).thenReturn(PLANET);

        // sut: system under test (sistema em teste)
        var sut = service.create(PLANET);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("Create Planet with invalid data throw exception")
    void createPlanet_WithInvalidData_ThrowException() {
        when(repository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> service.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Get Planet by existing id return Planet")
    void getByIdPlanet_ByExistingId_ReturnPlanet() {
        when(repository.findById(ID)).thenReturn(Optional.of(PLANET));

        var sut = service.getById(ID);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("Get Planet by unexisting id return empty")
    void getByIdPlanet_ByUnexistingId_ReturnEmpty() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        var sut = service.getById(ID);

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Get Planet by existing name return Planet")
    void getByNamePlanet_ByExistingName_ReturnPlanet() {
        when(repository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        var sut = service.getByName(PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("Get Planet by unexisting name return empty")
    void getByNamePlanet_ByUnexistingName_ReturnEmpty() {
        when(repository.findByName(INVALID_PLANET.getName())).thenReturn(Optional.empty());

        var sut = service.getByName(INVALID_PLANET.getName());

        assertThat(sut).isEmpty();
    }

}
