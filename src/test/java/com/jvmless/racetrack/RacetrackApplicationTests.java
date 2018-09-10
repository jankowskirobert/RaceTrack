package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagType;
import com.jvmless.racetrack.events.MessureEvent;
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
    public void shouldThrowException_threeSessions_onlyTwoAllowed() {

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

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_attacheEventToWrongSession() {

        LocalDateTime startTime = LocalDateTime.now();

        MessureEvent start = MessureEvent.of(
                startTime,
                Checkpoint.of(CheckpointType.START_META, "SM", 1),
                CompetitorNumber.of(5)
        );

        Track trackBerlin = getTrack("1");

        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), trackBerlin);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), trackBerlin);

        TrackEvent event1 = TrackEvent.of(start, secondSession);
        firstSession.attacheEvents(Arrays.asList(event1));

    }

    @Test
    public void shouldPass_sessionHistory() {

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

        Track trackBerlin = getTrack("1");
        Race trackDay = new Race(3, 30, LocalDateTime.now(), trackBerlin);
        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, LocalDateTime.now(), trackBerlin);
        TrackSession secondSession = TrackSession.of(ChronoUnit.MINUTES, 20, 15, LocalDateTime.now().plus(10, ChronoUnit.MINUTES), trackBerlin);
        trackDay.registerSession(firstSession, secondSession);

        TrackEvent event1 = TrackEvent.of(start, firstSession);
        TrackEvent event2 = TrackEvent.of(stop, secondSession);

        trackDay.attacheHistory(event1, event2);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_eventAfterTrackSession() {
        LocalDateTime trackSessionStart = LocalDateTime.now();
        LocalDateTime eventTime = trackSessionStart.plus(40, ChronoUnit.MINUTES);

        MessureEvent start = MessureEvent.of(
                eventTime,
                Checkpoint.of(CheckpointType.START_META, "SM", 1),
                CompetitorNumber.of(5)
        );

        Track trackBerlin = getTrack("1");

        TrackSession firstSession = TrackSession.of(ChronoUnit.MINUTES, 10, 15, trackSessionStart, trackBerlin);
        TrackEvent event1 = TrackEvent.of(start, firstSession);
        firstSession.sessionEnd();
        firstSession.attacheEvents(Arrays.asList(event1));
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
