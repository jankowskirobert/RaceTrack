package com.jvmless.racetrack;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = {"checkpointNumber"})
public class Checkpoint {
    private CheckpointType type;
    private String checkpointName;
    private int checkpointNumber;
}
