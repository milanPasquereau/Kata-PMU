package io.pasquereau.KataPMU.services;

import io.pasquereau.KataPMU.model.Race;

import java.util.List;

public interface RaceService {

    Race createRace(String name, List<String> namesJockeys);

    List<Race> findAllRaces();
}
