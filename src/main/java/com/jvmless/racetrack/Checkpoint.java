package com.jvmless.racetrack;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class Checkpoint {
    private CheckpointType type;
    private String checkpointName;
    private int checkpointNumber;
}
