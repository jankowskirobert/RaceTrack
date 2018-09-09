package com.jvmless.racetrack.events;

import com.jvmless.racetrack.CompetitorNumber;
import com.jvmless.racetrack.TrackSessionId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisqualifyingCompetitor {
    private RaceId raceId;
    private TrackSessionId id;
    private CompetitorNumber number;
    private String reason;
}
