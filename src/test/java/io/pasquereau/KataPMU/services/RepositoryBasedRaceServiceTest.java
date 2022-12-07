package io.pasquereau.KataPMU.services;

import io.pasquereau.KataPMU.model.Jockey;
import io.pasquereau.KataPMU.model.JockeyEntity;
import io.pasquereau.KataPMU.model.Race;
import io.pasquereau.KataPMU.model.RaceEntity;
import io.pasquereau.KataPMU.repositories.RaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryBasedRaceServiceTest {

    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private RepositoryBasedRaceService raceService;

    @Test
    @DisplayName("should create a race")
    void shouldCreateARace() {
        final UUID id = UUID.randomUUID();
        final LocalDate date = LocalDate.of(2022 , 2 , 11);
        final List<String> namesJockeys = List.of("Jean", "Marc","Luc");
        final List<JockeyEntity> jockeys = List.of(new JockeyEntity(1, "Jean"),
                new JockeyEntity(2, "Marc"),
                new JockeyEntity(3, "Luc"));
        final RaceEntity raceEntity = new RaceEntity(id,"Course 1", date, jockeys);
        final Race raceExpected = new Race(id,"Course 1", date, List.of(new Jockey(1, "Jean"),
                new Jockey(2, "Marc"),
                new Jockey(3, "Luc")));
        when(raceRepository.save(any())).thenReturn(raceEntity);

        Race raceResult = raceService.createRace("Course 1", namesJockeys);

        assertEquals(raceExpected, raceResult);
        InOrder inOrder = inOrder(raceRepository);
        inOrder.verify(raceRepository).save(any(RaceEntity.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("should find all races")
    void shouldFindAllRaces() {
        final UUID id = UUID.randomUUID();
        final LocalDate date = LocalDate.of(2022 , 2 , 11);
        final Iterable<RaceEntity> mockRaces = List.of(
                new RaceEntity(id,"Course 1", date, List.of(
                        new JockeyEntity(1, "Jean"),
                        new JockeyEntity(2, "Marc"),
                        new JockeyEntity(3, "Luc"))),
                new RaceEntity(id,"Course 1", date, List.of(
                        new JockeyEntity(1, "Jean"),
                        new JockeyEntity(2, "Marc"),
                        new JockeyEntity(3, "Luc"))));

        final List<Race> racesExpected = List.of(
                new Race(id,"Course 1", date, List.of(
                        new Jockey(1, "Jean"),
                        new Jockey(2, "Marc"),
                        new Jockey(3, "Luc"))),
                new Race(id,"Course 1", date, List.of(
                        new Jockey(1, "Jean"),
                        new Jockey(2, "Marc"),
                        new Jockey(3, "Luc"))));
        when(raceRepository.findAll()).thenReturn(mockRaces);

        List<Race> racesResult = raceService.findAllRaces();

        assertEquals(racesExpected, racesResult);
        InOrder inOrder = inOrder(raceRepository);
        inOrder.verify(raceRepository).findAll();
        inOrder.verifyNoMoreInteractions();
    }
}