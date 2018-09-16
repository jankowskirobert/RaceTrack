package com.jvmless.racetrack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class Competitor {

    private CompetitorNumber number;

}
