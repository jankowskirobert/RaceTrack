package com.jvmless.racetrack;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/*
podczas okrazenia nie moze wystapic wiecej jak jedna czerwona/zolta/zielona flaga
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackLap {

    TrackLapStatus status;
    LocalDateTime start;
    LocalDateTime stop;
    Duration bestLap;
    Duration lastLapTime;
    CompetitorNumber competitorNumber;
    Track track;

    public TrackLap(TrackSession trackSession, CompetitorNumber competitorNumber, List<TrackEvent> events) {
        Assert.isTrue(events.stream().allMatch(
                x -> x.getCompetitor().equals(competitorNumber)
        ), "Cannot apply events to given competitor");
        Assert.isTrue(events.stream().allMatch(
                x -> x.getTrackSession().equals(trackSession)
        ), "Cannot apply events to given track session");
        Assert.isTrue(events.stream().allMatch(
                x -> x.getOccurrence().isBefore(trackSession.getSessionStart())
        ));
        this.competitorNumber = competitorNumber;
        this.track = trackSession.getTrack();


//        events.stream().sorted(Comparator.comparing(x -> x.getOccurrence())).
    }

    public Duration getBestLapTime() {
        return Duration.between(start, stop);
    }
}
