package io.pasquereau.KataPMU.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RaceResponse(UUID number, String name, LocalDate date, List<Jockey> jockeys) {
}
