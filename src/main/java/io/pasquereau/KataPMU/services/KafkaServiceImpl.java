package io.pasquereau.KataPMU.services;

import io.pasquereau.KataPMU.model.Race;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService{

    @Override
    public void exposeRacesInAKafkaBus(List<Race> races) {
        //TODO
    }
}
