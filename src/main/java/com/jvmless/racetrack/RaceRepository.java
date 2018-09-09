package com.jvmless.racetrack;

public interface RaceRepository {
    Race findBySessionId(TrackSessionId id);
}
