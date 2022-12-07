package io.pasquereau.KataPMU.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public record RaceCreateRequest(@NotBlank String name, @Size(min=3) List<String> jockeys) {
}
