package br.com.starwars.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.starwars.commons.PlanetConstants.INVALID_PLANET;
import static br.com.starwars.commons.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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

    @Test
    @DisplayName("List Planets return all Planets")
    void listPlanets_ReturnAllPlanets() {
        var query = QueryBuilder.makeQuery(new Planet(PLANET.getTerrain(), PLANET.getClimate()));

        when(repository.findAll(query)).thenReturn(List.of(PLANET));

        var sut = service.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    @DisplayName("List Planets return no Planets")
    void listPlanets_ReturnNoPlanets() {
        when(repository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        var sut = service.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    @DisplayName("Remove Planet with existing id does not throw any Exception")
    void removePlanet_WithExistingId_doesNotThrowAnyException() {

        assertThatCode(() -> service.remove(ID)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Remove Planet with unexisting id throw Exception")
    void removePlanet_WithUnexistingId_ThrowException() {
        doThrow(new RuntimeException()).when(repository).deleteById(ID);

        assertThatThrownBy(() -> service.remove(ID)).isInstanceOf(RuntimeException.class);
    }
}
