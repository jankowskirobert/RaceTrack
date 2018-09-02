package com.jvmless.racetrack.events;

import com.jvmless.racetrack.CompetitorNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class FlagEvent {
    private FlagType type;
    private LocalDateTime occurrence;
    private CompetitorNumber number;
}
