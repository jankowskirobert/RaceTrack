package com.jvmless.racetrack.events;

import com.jvmless.racetrack.Checkpoint;
import com.jvmless.racetrack.CompetitorNumber;
import com.jvmless.racetrack.TrackEvent;
import com.jvmless.racetrack.TrackSession;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor(staticName = "of")
public class MeasureEvent implements TrackEvent {
    private LocalDateTime occurrence;
    private Checkpoint checkpoint;
    private CompetitorNumber number;

    @Override
    public TrackSession getTrackSession() {
        return null;
    }
}
