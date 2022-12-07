package io.pasquereau.KataPMU.repositories;

import io.pasquereau.KataPMU.model.RaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Repository
public interface RaceRepository extends CrudRepository<RaceEntity, UUID> {
}
