package io.pasquereau.KataPMU.model;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "JOCKEYS")
@Entity
public class JockeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int number;
    private String name;

    public JockeyEntity(int number, String name) {
        this(name);
        this.number = number;
    }

    public JockeyEntity(String name) {
        this.name = name;
    }

    public JockeyEntity(){}

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JockeyEntity that = (JockeyEntity) o;
        return number == that.number && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }
}
