package com.jvmless.racetrack;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
/*
Sesja sie zaczyna przed tym jak pojawią się okrążenia
Sesja konczy sie wraz z shachownica badz czerwona flaga
 */
@Getter
public class TrackSession {
    private List<TrackLap> records;
    private LocalDateTime sessionStart;
    private Track track;
    private LocalDateTime sessionEnd;

    public TrackSession(LocalDateTime sessionStart, Track track) {
        this.sessionStart = sessionStart;
        this.track = track;
    }

    public void attacheLaps(List<TrackLap> trackLaps){
        this.records = trackLaps;
    }

    public void sessionEnd() {
        this.sessionEnd = LocalDateTime.now();
    }


}
