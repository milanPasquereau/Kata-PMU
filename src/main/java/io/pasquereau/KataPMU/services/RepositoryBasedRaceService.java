package io.pasquereau.KataPMU.services;

import io.pasquereau.KataPMU.model.Jockey;
import io.pasquereau.KataPMU.model.JockeyEntity;
import io.pasquereau.KataPMU.model.Race;
import io.pasquereau.KataPMU.model.RaceEntity;
import io.pasquereau.KataPMU.repositories.RaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class RepositoryBasedRaceService implements RaceService {

    private final RaceRepository raceRepository;

    public RepositoryBasedRaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Race createRace(String name, List<String> jockeys) {
        return buildFromEntity(this.raceRepository.save(new RaceEntity(name, LocalDate.now(), buildJockeysEntitiesFromNames(jockeys))));
    }

    @Override
    public List<Race> findAllRaces() {
        return StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                .map(this::buildFromEntity)
                .toList();
    }

    private Race buildFromEntity(RaceEntity raceEntity) {
        return new Race(raceEntity.getNumber(), raceEntity.getName(), raceEntity.getDate(), buildJockeysFromEntities(raceEntity.getJockeys()));
    }

    private List<Jockey> buildJockeysFromEntities(List<JockeyEntity> jockeyEntities) {
        return jockeyEntities.stream().map(jockeyEntity -> new Jockey(jockeyEntity.getNumber(), jockeyEntity.getName())).toList();
    }

    private List<JockeyEntity> buildJockeysEntitiesFromNames(List<String> jockeys) {
        return jockeys.stream().map(JockeyEntity::new).toList();
    }
}
