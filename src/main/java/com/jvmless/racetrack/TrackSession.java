package com.jvmless.racetrack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/*
Sesja sie zaczyna przed tym jak pojawią się okrążenia
Sesja konczy sie wraz z shachownica badz czerwona flaga

ktos moze zjechac np do boxu juz w czasie trwania kolejnej sesji,
dodac event ktory mowi o zjezdzie do boxu i walidacje

 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackSession {
    private TrackSessionId sessionNumber;
    private Duration sessionDuration;
    private int competitorsCount;
    private List<TrackLap> records;
    private LocalDateTime sessionStart;
    private Track track;
    private LocalDateTime sessionEnd;

    public static TrackSession of(TemporalUnit unit, long unitValue, int numberOfCompetitors, LocalDateTime sessionStart, Track track) {
        track.validateMaxCompetitorsDuringSession(numberOfCompetitors);
        TrackSessionId sessionNumber = TrackSessionId.of(UUID.randomUUID().toString());
        return new TrackSession(sessionNumber, Duration.of(unitValue, unit), numberOfCompetitors, Collections.emptyList(), sessionStart, track, sessionStart.plus(unitValue, unit));
    }

    public void attacheLaps(List<TrackLap> trackLaps){
        this.records = trackLaps;
    }

    public void sessionEnd() {
        this.sessionEnd = LocalDateTime.now();
    }


}
