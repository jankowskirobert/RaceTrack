package com.jvmless.racetrack;

import com.jvmless.racetrack.events.RaceId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class Race {
    private RaceId raceId;
    private String description;
    private List<TrackSession> trackSessions;
    private int competitorsCount;
    private LocalDateTime raceDate;
    private List<Track> tracks;

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, Track... tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, description, Arrays.asList(tracks));
    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, Track... tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, null, Arrays.asList(tracks));
    }

    private void setUpRace(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, List<Track> tracks) {
        this.raceId = RaceId.of(UUID.randomUUID().toString());
        this.description = description;
        trackSessions = new ArrayList<>(sessionCount);
        this.raceDate = raceDate;
        this.competitorsCount = competitorsCount;
        this.tracks = tracks;
    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, List<Track> tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, description, tracks);
    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, List<Track> tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, null, tracks);
    }

    public void updateSessions(List<TrackSession> sessions) {
        update(sessions);
    }

    public void updateSessions(TrackSession... sessions) {
        update(Arrays.asList(sessions));
    }

    private void update(List<TrackSession> sessions) {
//        validateTrackSessionOnTrack();
        validateSessionStartEnd(sessions);
        validateSessionCompetitors(sessions);
        this.trackSessions = sessions;
    }

    private void validateSessionCompetitors(List<TrackSession> sessions) {
        int sumCompetitorsInSessions = sessions.stream().mapToInt(TrackSession::getCompetitorsCount).sum();
        if (sumCompetitorsInSessions > competitorsCount)
            throw new IllegalStateException("More competitors then registered for race!");
    }

    private void validateSessionStartEnd(List<TrackSession> sessions) {
        if (sessions.size() > 1)
            for (int i = 0; i < sessions.size() - 1; i++) {
                TrackSession trackSession = sessions.get(i);
                TrackSession nextTrackSession = sessions.get(i + 1);
                if (nextTrackSession.getSessionStart().isBefore(trackSession.getSessionEnd()) && trackSession.getTrack().equals(nextTrackSession.getTrack())) {
                    throw new IllegalStateException("Cannot handle two session simultaneously");
                }
            }
    }


}