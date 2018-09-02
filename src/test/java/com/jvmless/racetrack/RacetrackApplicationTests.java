package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagEvent;
import com.jvmless.racetrack.events.FlagType;
import com.jvmless.racetrack.events.MessureEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RacetrackApplicationTests {

    @Test
    public void contextLoads() {
        Track track = Track.create(
                "Silverstone",
                Arrays.asList(
                        Checkpoint.of(
                                CheckpointType.START_META,
                                "SM",
                                1)
                )
        );

        MessureEvent messureEvent = MessureEvent.of(
                LocalDateTime.now(),
                Checkpoint.of(CheckpointType.START_META, "SM", 1),
                CompetitorNumber.of(5)
        );
        
        TrackEvent event1 = TrackEvent.of(track, messureEvent);
        FlagEvent flagEvent = FlagEvent.of(FlagType.YELLOW, LocalDateTime.now(), CompetitorNumber.of(5));
        TrackEvent event2 = TrackEvent.of(track, flagEvent);
        List<TrackEvent> eventList = Arrays.asList(event1, event2);
        TrackLap trackLap = new TrackLap(CompetitorNumber.of(5), eventList);
    }

}
