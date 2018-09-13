package com.jvmless.racetrack;

import com.jvmless.racetrack.events.RaceId;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
SessionCount per Track!!!!
 */
@Getter
public class Race {

    private RaceId raceId;
    private String description;
    private int maxSessions;
    private Set<TrackSession> trackSessions;
    private int competitorsCount;
    private LocalDateTime raceDate;
    private List<Track> tracks;
    private RaceType type;

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, Track... tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, description, Arrays.stream(tracks).collect(Collectors.toList()));
    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, Track... tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, null, Arrays.stream(tracks).collect(Collectors.toList()));
    }

    private void setUpRace(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, List<Track> tracks) {
        this.raceId = RaceId.of(UUID.randomUUID().toString());
        this.description = description;
        this.raceDate = raceDate;
        this.competitorsCount = competitorsCount;
        this.tracks = tracks;
        this.trackSessions = new HashSet<>();
        setRaceTypeAndSessionCount(sessionCount, tracks);
    }

    private void setRaceTypeAndSessionCount(int sessionCount, List<Track> tracks) {
        int trackSize = tracks.size();
        if (trackSize > 1) {
            type = RaceType.SPLIT;
            maxSessions = sessionCount * trackSize;
        } else {
            if (sessionCount > 1)
                type = RaceType.MULTI_SESSION;
            else
                type = RaceType.SINGLE_SESSION;
            maxSessions = sessionCount;
        }

    }

//    public void attacheHistory(@NonNull List<TrackEvent> events) {
//        history(events);
//    }
//
//    public void attacheHistory(@NonNull TrackEvent... events) {
//        history(Arrays.asList(events));
//    }

//    private void history(@NonNull List<? extends TrackEvent> events) {
//        trackSessions.stream()
//                .forEach(
//                        session -> session.attacheMeasureEvents(
//                                events.stream()
//                                        .filter(event -> event.getTrackSession().equals(session))
//                                        .collect(Collectors.toList())
//                        )
//                );
//    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, String description, @NonNull List<Track> tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, description, tracks);
    }

    public Race(int sessionCount, int competitorsCount, LocalDateTime raceDate, @NonNull List<Track> tracks) {
        setUpRace(sessionCount, competitorsCount, raceDate, null, tracks);
    }

    public void registerSession(@NonNull List<TrackSession> sessions) {
        register(sessions);
    }

    public void registerSession(@NonNull TrackSession... sessions) {
        register(Arrays.asList(sessions));
    }

    private void register(@NonNull List<TrackSession> sessions) {
        validateTrackSessionOnTrack(sessions);
        validateSessionStartEnd(sessions);
        validateSessionCompetitors(sessions);
        validateAddTrackSessions(sessions);
    }

    private void validateAddTrackSessions(@NonNull List<TrackSession> sessions) {
        if (!sessions.isEmpty()) {
            if ((trackSessions == null || trackSessions.isEmpty())) {
                this.trackSessions.addAll(sessions);
                return;
            }
            if (trackSessions.size() + sessions.size() <= maxSessions) {
                trackSessions.addAll(sessions);
            } else {
                throw new IllegalArgumentException(String.format("Current sessions size: %d, max: %d, new sessions: %d", trackSessions.size(), maxSessions, sessions.size()));
            }
        } else
            throw new IllegalArgumentException("Cannot add empty track session");
    }

    public int getMaxSessionCount() {
        return maxSessions;
    }

    private void validateTrackSessionOnTrack(List<TrackSession> sessions) {
        boolean sessionBelongsToTrack = sessions.stream().map(x -> x.getTrack()).distinct().allMatch(Track.isIncluded(tracks));
        if (!sessionBelongsToTrack)
            throw new IllegalArgumentException("Cannot handle session for unconfigured tracks");
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
