package com.jvmless.racetrack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Race {
    private List<TrackSession> trackSessions;
    private int competitorsCount;
    private LocalDateTime raceDate;
    private Track track;

    public Race(Track track, int sessionCount, int competitorsCount, LocalDateTime raceDate) {
        trackSessions = new ArrayList<>(sessionCount);
        this.raceDate = raceDate;
        this.competitorsCount = competitorsCount;
        this.track = track;
    }

    public void updateSessions(List<TrackSession> sessions) {
        update(sessions);
    }

    public void updateSessions(TrackSession... sessions) {
        update(Arrays.asList(sessions));
    }

    private void update(List<TrackSession> sessions) {
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
                if (nextTrackSession.getSessionStart().isBefore(trackSession.getSessionEnd())) {
                    throw new IllegalStateException("Cannot handle two session simultaneously");
                }
            }
    }


}
