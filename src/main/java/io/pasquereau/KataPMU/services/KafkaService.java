package io.pasquereau.KataPMU.services;

import io.pasquereau.KataPMU.model.Race;

import java.util.List;

public interface KafkaService {

    void exposeRacesInAKafkaBus(List<Race> races);
}
