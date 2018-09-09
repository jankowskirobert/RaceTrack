package com.jvmless.racetrack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class TrackId {
    private String id;
}
