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
    private Track track;

    public static TrackEvent of(Track track, FlagEvent flagEvent) {
        TrackEvent trackEvent = new TrackEvent(flagEvent.getOccurrence(), TrackEventType.FLAG, flagEvent.getNumber(), track);
//        track.validateFlag(flagEvent);
        return trackEvent;
    }

    public static TrackEvent of(Track track, MessureEvent messureEvent) {
        TrackEvent trackEvent = new TrackEvent(messureEvent.getOccurrence(), TrackEventType.CHECKPOINT, messureEvent.getNumber(), track);
        track.validateMessure(messureEvent);
        return trackEvent;
    }

    public TrackEvent(LocalDateTime occurrence, TrackEventType trackEventType, CompetitorNumber competitor,Track track) {
        this.occurrence = occurrence;
        this.trackEventType = trackEventType;
        this.competitor = competitor;
        this.track = track;
    }
}
