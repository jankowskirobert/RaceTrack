package com.jvmless.racetrack;

public enum CheckpointType {
    TRACK_SECTION(false), START_META(true), START(true), META(false);

    private boolean initialCheckpoint;
    CheckpointType(boolean b) {
        this.initialCheckpoint = b;
    }

    public boolean isInitialCheckpoint() {
        return initialCheckpoint;
    }
}
