package com.jvmless.racetrack;

import java.time.LocalDateTime;
/*
zrobic jako interfejs albo klasa abstrakcyjna,
lepiej aby session przyjmowl poszczegolne eventy ze wzgledu na to ze
przy daleszej rozbudowie lepiej zachowac poszczegolne eventy jako crudowe serwisy

 */
public interface TrackEvent {
    TrackSession getTrackSession();
    LocalDateTime getOccurrence();
}
