package com.jvmless.racetrack;

import com.jvmless.racetrack.events.DisqualifyingCompetitor;

import java.time.LocalDateTime;

public class DisqualifyingCompetitorHandler {

    RaceRepository raceRepository;

    public void handle(DisqualifyingCompetitor disqualifyingCompetitor) {
        LocalDateTime currentDate = LocalDateTime.now();
        Race race = raceRepository.findBySessionId(disqualifyingCompetitor.getId());
    }

}
