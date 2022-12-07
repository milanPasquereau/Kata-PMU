package io.pasquereau.KataPMU.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "RACES")
@Entity
public class RaceEntity {

    @GeneratedValue(generator="system-uuid")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Id
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID number;
    private String name;
    private LocalDate date;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<JockeyEntity> jockeys;

    public RaceEntity(String name, LocalDate date, List<JockeyEntity> jockeys) {
        this.name = name;
        this.date = date;
        this.jockeys = jockeys;
    }

    public RaceEntity(UUID number, String name, LocalDate date, List<JockeyEntity> jockeys) {
        this(name, date, jockeys);
        this.number = number;
    }

    public RaceEntity(){}

    public UUID getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<JockeyEntity> getJockeys() {
        return jockeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceEntity that = (RaceEntity) o;
        return Objects.equals(number, that.number) && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(jockeys, that.jockeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, date, jockeys);
    }
}
