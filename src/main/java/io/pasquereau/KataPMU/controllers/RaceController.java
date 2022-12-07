package io.pasquereau.KataPMU.controllers;

import io.pasquereau.KataPMU.model.Race;
import io.pasquereau.KataPMU.model.RaceCreateRequest;
import io.pasquereau.KataPMU.model.RaceResponse;
import io.pasquereau.KataPMU.services.KafkaService;
import io.pasquereau.KataPMU.services.RaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;
    private final KafkaService kafkaService;

    public RaceController(RaceService raceService, KafkaService kafkaService) {
        this.raceService = raceService;
        this.kafkaService = kafkaService;
    }

    @GetMapping
    public List<RaceResponse> findAllRaces() {
        List<Race> races = this.raceService.findAllRaces();
        kafkaService.exposeRacesInAKafkaBus(races);
        return races.stream().map(this::buildResponse).toList();
    }

    @PostMapping
    public ResponseEntity<RaceResponse> createRace(@Valid @RequestBody RaceCreateRequest raceCreateRequest) {
        return new ResponseEntity<>(this.buildResponse(
                raceService.createRace(raceCreateRequest.name(), raceCreateRequest.jockeys())), HttpStatus.CREATED);
    }

    private RaceResponse buildResponse(Race race) {
        return new RaceResponse(
                race.getNumber(),
                race.getName(),
                race.getDate(),
                race.getJockeys());
    }
}
