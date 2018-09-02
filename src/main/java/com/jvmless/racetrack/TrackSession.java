package com.jvmless.racetrack;

import java.util.Comparator;
import java.util.List;

public class TrackSession {
    List<TrackLap> records;

    TrackLap getBestLap(){
        return records.stream().max(Comparator.comparing(TrackLap::getTotalLapTime)).get();
    }
}
