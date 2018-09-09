package com.jvmless.racetrack;

import com.jvmless.racetrack.events.FlagType;
import com.jvmless.racetrack.events.MessureEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Builder
public class Track {
    private TrackId trackId;
    private String name;
    private List<Checkpoint> checkpointList;
    private List<FlagType> finalFlags;
    private Integer maximumCompetitorsInOneSession;

    //    private TrackLap bestTrackLap;
    public void validateMessure(final MessureEvent messureEvent) {
        if (!checkpointList.contains(messureEvent.getCheckpoint()))
            throw new IllegalStateException("Track do not contain checkpoint");
    }

    public boolean isFinalFlag(FlagType flagType) {
        return finalFlags.contains(flagType);
    }

    public void validateMaxCompetitorsDuringSession(int numberOfCompetitors) {
        if (!Objects.isNull(maximumCompetitorsInOneSession) && maximumCompetitorsInOneSession.intValue() < numberOfCompetitors)
            throw new IllegalStateException(
                    String.format(
                            "Track cannot handle more then %d competitors in one session, maximum is %d",
                            numberOfCompetitors,
                            maximumCompetitorsInOneSession.intValue()
                    )
            );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(trackId, track.trackId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId);
    }

    public static Predicate<Track> isIncluded(List<Track> track){
        return x -> track.contains(x);
    }
}
