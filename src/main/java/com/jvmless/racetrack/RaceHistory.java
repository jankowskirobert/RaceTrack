package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagEvent;
import com.jvmless.racetrack.events.MeasureEvent;
import com.jvmless.racetrack.winstrategy.WinStrategy;

import java.util.Set;

public class RaceHistory {
    private Set<FlagEvent> flags;
    private Set<MeasureEvent> measures;

    public RaceHistory(Set<FlagEvent> flags, Set<MeasureEvent> measures) {
        this.flags = flags;
        this.measures = measures;
    }

    public void bestLap(WinStrategy strategy) {

    }

    public long numberOfCompetitors() {
        long fromFlags = flags.stream().map(x -> x.getNumber()).distinct().count();
        long fromMeasures = measures.stream().map(x -> x.getNumber()).distinct().count();
        return fromFlags + fromMeasures;
    }
 }
