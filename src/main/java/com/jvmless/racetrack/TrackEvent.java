package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagEvent;
import com.jvmless.racetrack.events.MessureEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackEvent {
    private LocalDateTime occurrence;
    private TrackEventType trackEventType;
    private CompetitorNumber competitor;
    private FlagEvent lastFlagEvent;
    private MessureEvent lastMessureEvent;
    private TrackSession trackSession;

    public static TrackEvent of(FlagEvent flagEvent, TrackSession trackSession) {
        TrackEvent trackEvent = new TrackEvent(flagEvent.getOccurrence(), TrackEventType.FLAG, flagEvent.getNumber(), trackSession);
        if(trackSession.getTrack().isFinalFlag(flagEvent.getType()))
            trackSession.sessionEnd();
        return trackEvent;
    }

    public static TrackEvent of(MessureEvent messureEvent, TrackSession trackSession) {
        TrackEvent trackEvent = new TrackEvent(messureEvent.getOccurrence(), TrackEventType.CHECKPOINT, messureEvent.getNumber(), trackSession);
        trackSession.getTrack().validateMessure(messureEvent);
        return trackEvent;
    }

    public TrackEvent(LocalDateTime occurrence, TrackEventType trackEventType, CompetitorNumber competitor,TrackSession trackSession) {
        this.occurrence = occurrence;
        this.trackEventType = trackEventType;
        this.competitor = competitor;
        this.trackSession = trackSession;
    }
}
