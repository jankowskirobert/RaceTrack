package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagEvent;
import com.jvmless.racetrack.events.MeasureEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Sesja sie zaczyna przed tym jak pojawią się okrążenia
Sesja konczy sie wraz z shachownica badz czerwona flaga

ktos moze zjechac np do boxu juz w czasie trwania kolejnej sesji,
dodac event ktory mowi o zjezdzie do boxu i walidacje

 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackSession {
    private TrackSessionId sessionNumber;
//    private Duration sessionDuration;
    private int competitorsCount;
    private List<TrackLap> records;
    private LocalDateTime sessionStart;
    private Track track;
    private LocalDateTime sessionEnd;

    public static TrackSession of(TemporalUnit unit, long unitValue, int numberOfCompetitors, LocalDateTime sessionStart, Track track) {
        track.validateMaxCompetitorsDuringSession(numberOfCompetitors);
        TrackSessionId sessionNumber = TrackSessionId.of(UUID.randomUUID().toString());
        return new TrackSession(sessionNumber, numberOfCompetitors, Collections.emptyList(), sessionStart, track, sessionStart.plus(unitValue, unit));
    }

    public void attacheMeasureEvents(@NonNull final List<MeasureEvent> events) {
        validateEvents(events);
        validateRiders(events);
        List<Checkpoint> checkpointList = track.getCheckpointList();
        Stream<MeasureEvent> sortedTime = events.stream().sorted(Comparator.comparing(MeasureEvent::getOccurrence));
        List<Checkpoint> sortedMeasure = sortedTime.map(x -> x.getCheckpoint()).collect(Collectors.toList());
        List<Checkpoint> measure = sortedMeasure.subList(0, checkpointList.size());
        if (!checkpointList.equals(measure))
            throw new IllegalStateException("Checkpoint not match track specification");
    }

    private void validateRiders(@NonNull List<MeasureEvent> events) {
        long uniqueRiders = events.stream().map(MeasureEvent::getNumber).distinct().count();
        if (uniqueRiders > competitorsCount)
            throw new IllegalStateException("More riders in session then registered");
    }

    public void attacheFlagEvents(@NonNull final List<FlagEvent> events) {
        validateEvents(events);
    }

    private void validateEvents(@NonNull List<? extends TrackEvent> events) {
        validateTrackSessionMatchEvents(events);
        validateEventAfterEndTime(events);
        validateEventBeforeStartTime(events);
    }

    private void validateEventBeforeStartTime(List<? extends TrackEvent> events) {
        Stream<LocalDateTime> localDateTimeStream = events.stream().map(TrackEvent::getOccurrence);
        /*
        zwalidowac eventy dla poszczegolnych zawodnikow,
        nie moze byc dwoch eventow pomiarowych dla tego samego zawodnika w tym samym czasie
         */

        localDateTimeStream.min(Comparator.naturalOrder()).ifPresent(
                x -> {
                    if (x.isBefore(sessionStart))
                        throw new IllegalStateException("Cannot add event that is before track session");
                }
        );
    }

    private void validateTrackSessionMatchEvents(List<? extends TrackEvent> events) {
        if (events.stream().map(TrackEvent::getTrackSession).noneMatch(sessionOfEvent -> sessionOfEvent.equals(this)))
            throw new IllegalArgumentException("Cannot attache event to current session");


    }

    private void validateEventAfterEndTime(List<? extends TrackEvent> events) {
        events.stream().map(TrackEvent::getOccurrence).max(Comparator.naturalOrder()).ifPresent(
                x -> {
                    if (x.isAfter(sessionEnd))
                        throw new IllegalStateException("Cannot add event that is after track session");
                }
        );
    }

    public void sessionEnd() {
        this.sessionEnd = LocalDateTime.now();
    }

    public boolean nextSessionValid(TrackSession nextSession){
        boolean after = nextSession.sessionStart.isAfter(this.sessionEnd);
        boolean sameTrack = this.track.equals(nextSession.track);
        return after && sameTrack;
    }

}
