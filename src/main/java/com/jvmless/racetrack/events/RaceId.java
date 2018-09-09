package com.jvmless.racetrack.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RaceId {
    private String id;
}
