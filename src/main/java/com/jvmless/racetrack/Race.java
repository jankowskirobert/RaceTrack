package com.jvmless.racetrack;

import com.jvmless.racetrack.events.RaceId;
import com.jvmless.racetrack.winstrategy.WinStrategy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

/*
SessionCount per Track!!!!
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Race {
    //unique entity id
    private Long id;
    //Race id can be few with same
    private RaceId raceId;
    // opis
    private String description;
    // number of competitors in race
    private int competitorsCount;
    // data startu
    private LocalDateTime raceDate;
    //list tras - set dla rownoczesnych wyscigow
    private Track tracks;
    //typ wyscigu co to znaczy?
    private RaceStatus status;
    //forma rozliczenia
    private WinStrategy winStrategy;
    //okrazenia uzyskane
    private Set<TrackLap> records;
    //koniec wyscigu
    private LocalDateTime raceEnd;
    //
    private Set<CompetitorNumber> competitorNumbers;
    //
    private Set<CompetitorNumber> disqualifiedNumbers;

    public static Race prepare(RaceId raceId, int competitorsCount, Track track, WinStrategy winStrategy, RaceHistory raceHistory) {
        if(competitorsCount != raceHistory.numberOfCompetitors())
            throw new IllegalStateException("Incorrect number of competitors");
        return new Race(
                new Random().nextLong(),
                raceId,
                "",
                competitorsCount,
                null,
                track,
                RaceStatus.INITIALIZED,
                winStrategy,
                new HashSet<>(),
                null,
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public void registerCompetitor(final Competitor competitor) {
        if (competitorNumbers.size() < competitorsCount && status.equals(RaceStatus.INITIALIZED))
            this.competitorNumbers.add(competitor.number());
        else
            throw new IllegalStateException("Cannot register new competitor, max number reached");
    }

    public void disqualifyCompetitor(final Competitor competitor) {
        if (disqualifiedNumbers.contains(competitor.number()) || !competitorNumbers.contains(competitor.number()))
            throw new IllegalStateException("Cannot disqualified competitor");
        this.disqualifiedNumbers.add(competitor.number());
    }

    public void finish() {
        if(status.equals(RaceStatus.INITIALIZED) || status.equals(RaceStatus.STARTED)) {
            this.status = RaceStatus.FINALISED;
            this.raceEnd = LocalDateTime.now();
        } else
            throw new IllegalStateException("Cannot finish race, is already ended");
    }

    public void cancel(){
        if(status.equals(RaceStatus.INITIALIZED) || status.equals(RaceStatus.STARTED)) {
            this.status = RaceStatus.CANCELED;
            this.raceEnd = LocalDateTime.now();
        } else
            throw new IllegalStateException("Cannot finish race, is already ended");
    }

    public void terminate(){
        if(status.equals(RaceStatus.INITIALIZED) || status.equals(RaceStatus.STARTED)) {
            this.status = RaceStatus.TERMINATED;
            this.raceEnd = LocalDateTime.now();
        } else
            throw new IllegalStateException("Cannot finish race, is already ended");
    }

    public void validateCompetitorInRace(final Competitor competitor) {

    }

}
