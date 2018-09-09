package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RacetrackApplicationTests {

    @Test
    public void shouldPass_sessionContainsLowerCompetitorsThenSignedUpForRace() {


        Track track = getTrack("1");

        Race trackDay = new Race(3, 30, LocalDateTime.now(), track);
        TrackSession session = TrackSession.of(ChronoUnit.MINUTES, 10, 5, LocalDateTime.now(), track);
        trackDay.registerSession(session);
        Assert.assertThat(trackDay.getMaxSessionCount(), Matchers.is(3));
        Assert.assertThat(trackDay.getType(), Matchers.is(RaceType.MULTI_SESSION));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_moreCompetitorsInSessionThenSignedUp() {

        Track track = getTrack("1");

        Race trackDay = new Race(3, 30, LocalDateTime.now(), track);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 25, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        trackDay.registerSession(firstSession, secondSession);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_twoSessionAtTheSameTime() {

        Track track = getTrack("1");

        Race trackDay = new Race(3, 30, LocalDateTime.now(), track);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now(), track);
        trackDay.registerSession(firstSession, secondSession);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_threeSessions() {

        Track track = getTrack("1");

        Race trackDay = new Race(2, 30, LocalDateTime.now(), track);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        TrackSession thirdSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        trackDay.registerSession(firstSession, secondSession);
        trackDay.registerSession(thirdSession);
    }

    @Test
    public void shouldPass_threeSessions() {

        Track track = getTrack("1");

        Race trackDay = new Race(3, 30, LocalDateTime.now(), track);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), track);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        TrackSession thirdSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), track);
        trackDay.registerSession(firstSession, secondSession);
        trackDay.registerSession(thirdSession);
    }

    @Test
    public void shouldPass_twoDifferentSessionsSimultaneouslyOnDifferentTrack_sameRaceScope() {


        Track trackBerlin = getTrack("1");
        Track trackPoznan = getTrack("2");
        Race trackDay = new Race(3, 30, LocalDateTime.now(), trackBerlin, trackPoznan);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), trackBerlin);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now(), trackPoznan);
        trackDay.registerSession(firstSession, secondSession);
        Assert.assertThat(trackDay.getMaxSessionCount(), Matchers.is(6));
        Assert.assertThat(trackDay.getType(), Matchers.is(RaceType.SPLIT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_trackSessionForDifferentTrack() {


        Track trackBerlin = getTrack("1");
        Track trackPoznan = getTrack("2");
        Race trackDay = new Race(3, 30, LocalDateTime.now(), trackBerlin);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), trackBerlin);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now(), trackPoznan);
        trackDay.registerSession(firstSession, secondSession);
    }



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
