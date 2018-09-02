package com.jvmless.racetrack.events;

import com.jvmless.racetrack.Checkpoint;
import com.jvmless.racetrack.CompetitorNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor(staticName = "of")
public class MessureEvent {
    private LocalDateTime occurrence;
    private Checkpoint checkpoint;
    private CompetitorNumber number;
}
