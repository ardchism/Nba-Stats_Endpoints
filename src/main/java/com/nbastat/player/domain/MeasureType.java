package com.nbastat.player.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeasureType implements ApiParameter {

    Advanced("Advanced"), Base("Base"), Defense("Defense"),
    FourFactors("Four Factors"), Misc("Misc"), Opponent("Opponent"),
    Scoring("Scoring"), Usage("Usage");

    private String value;

    @Override
    public ApiParameter getDefaultValue() {
        return Base;
    }
}
