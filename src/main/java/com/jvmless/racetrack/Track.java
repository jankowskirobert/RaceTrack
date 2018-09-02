package com.jvmless.racetrack;

import com.jvmless.racetrack.events.MessureEvent;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor(staticName = "create")
public class Track {
    private String name;
    private List<Checkpoint> checkpointList;
//    private TrackLap bestTrackLap;

    public void validateMessure(final MessureEvent messureEvent) {
        if(!checkpointList.contains(messureEvent.getCheckpoint()))
            throw new IllegalStateException("Track do not contain checkpoint");
    }
}
