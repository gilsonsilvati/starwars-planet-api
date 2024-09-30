package br.com.starwars.web;

import br.com.starwars.domain.Planet;
import br.com.starwars.domain.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static br.com.starwars.commons.PlanetConstants.INVALID_PLANET_REQUEST;
import static br.com.starwars.commons.PlanetConstants.PLANET;
import static br.com.starwars.commons.PlanetConstants.PLANET_REQUEST;
import static br.com.starwars.commons.PlanetConstants.PLANET_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanetController.class)
class PlanetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PlanetService service;

    static final String URL = "/api/v1/planets";
    static final Integer ID = 1;

    @Test
    @DisplayName("Create Planet with valid data return Created")
    void createPlanet_WithValidData_ReturnCreated() throws Exception {
        when(service.create(PLANET_REQUEST.toPlanet())).thenReturn(PLANET);

        mockMvc.perform(post(URL)
                        .content(mapper.writeValueAsString(PLANET_REQUEST))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(PLANET_RESPONSE)));
    }

    @Test
    @DisplayName("Create Planet with invalid data return UnprocessableEntity")
    void createPlanet_WithInvalidData_ReturnUnprocessableEntity() throws Exception {
        when(service.create(INVALID_PLANET_REQUEST.toPlanet())).thenReturn(PLANET);

        mockMvc.perform(post(URL)
                        .content(mapper.writeValueAsString(INVALID_PLANET_REQUEST))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Create Planet with existing name return Conflict")
    void createPlanet_WithExistingName_ReturnConflict() throws Exception {
        when(service.create(any(Planet.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post(URL)
                        .content(mapper.writeValueAsString(PLANET_REQUEST))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Get Planet with by existing id return OK")
    void getPlanet_WithByExistingId_ReturnOK() throws Exception {
        when(service.getById(ID)).thenReturn(Optional.of(PLANET));

        mockMvc.perform(get(URL + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(PLANET_RESPONSE)));
    }

    @Test
    @DisplayName("Get Planet with by unexisting id return Not Found")
    void getPlanet_WithByUnexistingId_ReturnNotFound() throws Exception {
        mockMvc.perform(get(URL + "/" + ID))
                .andExpect(status().isNotFound());
    }
}
