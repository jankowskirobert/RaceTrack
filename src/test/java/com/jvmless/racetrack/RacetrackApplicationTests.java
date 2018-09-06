package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagEvent;
import com.jvmless.racetrack.events.FlagType;
import com.jvmless.racetrack.events.MessureEvent;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RacetrackApplicationTests {

    @Test
    public void shouldPass_sessionContainsLowerCompetitorsThenSignedUpForRace() {


        Track track = getTrack();

        Race trackDay = new Race(track, 3, 30, LocalDateTime.now());
        TrackSession session = TrackSession.of(ChronoUnit.MINUTES, 10, 5, LocalDateTime.now(), track);
        trackDay.updateSessions(session);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_moreCompetitorsInSessionThenSignedUp() {


        Track track = getTrack();

        Race trackDay = new Race(track, 3, 30, LocalDateTime.now());
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 25, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        trackDay.updateSessions(firstSession, secondSession);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_twoSessionAtTheSameTime() {


        Track track = getTrack();

        Race trackDay = new Race(track, 3, 30, LocalDateTime.now());
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now(), track);
        trackDay.updateSessions(firstSession, secondSession);
    }

    private Track getTrack() {
        return Track.builder()
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
     /*

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime stopTime = startTime.plus(10, ChronoUnit.MINUTES);
        MessureEvent start = MessureEvent.of(
                startTime,
                Checkpoint.of(CheckpointType.START_META, "SM", 1),
                CompetitorNumber.of(5)
        );

        MessureEvent stop = MessureEvent.of(
                stopTime,
                Checkpoint.of(CheckpointType.START_META, "SM", 1),
                CompetitorNumber.of(5)
        );
        TrackSession trackSession = new TrackSession(startTime.minus(1, ChronoUnit.MINUTES), track);
        TrackEvent event1 = TrackEvent.of(start, trackSession);
        TrackEvent event2 = TrackEvent.of(stop, trackSession);
        FlagEvent flagEvent = FlagEvent.of(FlagType.YELLOW, LocalDateTime.now(), CompetitorNumber.of(5));
        TrackEvent event3 = TrackEvent.of(flagEvent, trackSession);
        List<TrackEvent> eventList = Arrays.asList(event1, event2);
        TrackLap trackLap = new TrackLap(trackSession, CompetitorNumber.of(5), eventList);
        Assert.assertThat(trackLap.getTotalLapTime(), Matchers.equalTo(Duration.of(10, ChronoUnit.MINUTES)));
      */

}
