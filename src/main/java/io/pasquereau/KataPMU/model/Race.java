package io.pasquereau.KataPMU.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Race {
    private UUID number;
    private String name;
    private LocalDate date;
    private List<Jockey> jockeys;

    public Race(UUID number, String name, LocalDate date, List<Jockey> jockeys) {
        this.number = number;
        this.name = name;
        this.date = date;
        this.jockeys = jockeys;
    }

    public UUID getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Jockey> getJockeys() {
        return jockeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(number, race.number) && Objects.equals(name, race.name) && Objects.equals(date, race.date) && Objects.equals(jockeys, race.jockeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, date, jockeys);
    }
}
