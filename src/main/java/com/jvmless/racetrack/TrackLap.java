package com.jvmless.racetrack;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackLap {

    TrackLapStatus status;
    LocalDateTime start;
    LocalDateTime stop;
    CompetitorNumber competitorNumber;


    public TrackLap(CompetitorNumber competitorNumber, List<TrackEvent> events) {

    }
    public Duration getTotalLapTime() {
        return Duration.between(start, stop);
    }
}
