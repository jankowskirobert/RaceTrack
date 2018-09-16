package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagType;
import com.jvmless.racetrack.events.MeasureEvent;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RacetrackApplicationTests {



    private Track getTrack(String id) {
        return Track.builder()
                .trackId(TrackId.of(id))
                .name("Silverstone")
                .checkpointList(
                        Arrays.asList(
                                Checkpoint.of(
                                        CheckpointType.START_META,
                                        "SM",
                                        1
                                )
                        )
                )
                .finalFlags(
                        Arrays.asList(FlagType.RED)
                )
                .build();
    }

}
