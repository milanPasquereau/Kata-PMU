package io.pasquereau.KataPMU.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pasquereau.KataPMU.model.Jockey;
import io.pasquereau.KataPMU.model.Race;
import io.pasquereau.KataPMU.model.RaceCreateRequest;
import io.pasquereau.KataPMU.model.RaceResponse;
import io.pasquereau.KataPMU.services.KafkaService;
import io.pasquereau.KataPMU.services.RaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class RaceControllerTest {

    @MockBean
    private RaceService raceService;

    @MockBean
    private KafkaService kafkaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("should create a valid race")
    void shouldCreateValidRace() throws Exception {
        final UUID id = UUID.randomUUID();
        final List<String> namesJockeys = List.of("Jean", "Marc","Luc");
        final List<Jockey> jockeys = List.of(new Jockey(1, "Jean"),
                new Jockey(2, "Marc"),
                new Jockey(3, "Luc"));
        final LocalDate date = LocalDate.of(2022 , 2 , 11);
        final Race race = new Race(id,"Course 1", date, jockeys);
        final RaceResponse expectedResponse = new RaceResponse(id, "Course 1", date, jockeys);

        when(raceService.createRace("Course 1", namesJockeys)).thenReturn(race);

        mockMvc.perform(post("/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RaceCreateRequest("Course 1", namesJockeys))))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse), true));

        verify(raceService).createRace("Course 1", namesJockeys);
        verifyNoMoreInteractions(raceService);
    }

    @Test
    @DisplayName("should not create race with less than 3 jockeys")
    void shouldNotCreateRaceWithLessThan3Jockeys() throws Exception {
        final List<String> namesJockeys = List.of("Jean", "Marc");

        mockMvc.perform(post("/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RaceCreateRequest("Course 1", namesJockeys))))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(raceService);
    }

    @Test
    @DisplayName("should not create race with blank name")
    void shouldNotCreateRaceWithBlankName() throws Exception {
        final List<String> namesJockeys = List.of("Jean", "Marc");

        mockMvc.perform(post("/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RaceCreateRequest("   ", namesJockeys))))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(raceService);
    }


    @Test
    @DisplayName("should find and expose all races")
    void shouldFindAndExposeAllRaces() throws Exception {
        final UUID id = UUID.randomUUID();
        final LocalDate date = LocalDate.of(2022 , 2 , 11);
        final List<Jockey> jockeys = List.of(new Jockey(1, "Jean"),
                new Jockey(2, "Marc"),
                new Jockey(3, "Luc"));
        final RaceResponse firstExpectedResponse = new RaceResponse(id, "Course 1", date, jockeys);
        final RaceResponse secondExpectedResponse = new RaceResponse(id, "Course 2", date, jockeys);
        final List<Race> mockRaces = List.of(
                new Race(id,"Course 1", date, List.of(
                        new Jockey(1, "Jean"),
                        new Jockey(2, "Marc"),
                        new Jockey(3, "Luc"))),
                new Race(id,"Course 2", date, List.of(
                        new Jockey(1, "Jean"),
                        new Jockey(2, "Marc"),
                        new Jockey(3, "Luc"))));
        when(raceService.findAllRaces()).thenReturn(mockRaces);

        mockMvc.perform(get("/races")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(List.of(firstExpectedResponse, secondExpectedResponse)), true));

        verify(raceService).findAllRaces();
        verify(kafkaService).exposeRacesInAKafkaBus(mockRaces);
        verifyNoMoreInteractions(raceService);
    }

}