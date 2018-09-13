package com.jvmless.racetrack.events;

import com.jvmless.racetrack.CompetitorNumber;
import com.jvmless.racetrack.TrackEvent;
import com.jvmless.racetrack.TrackSession;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class FlagEvent implements TrackEvent {
    private FlagType type;
    private LocalDateTime occurrence;
    private CompetitorNumber number;
    private TrackSession trackSession;
}
