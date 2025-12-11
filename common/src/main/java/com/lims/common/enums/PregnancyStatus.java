package com.lims.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PregnancyStatus {
    PREGNANT,
    NON,
    NOT_PREGNANT,
    PREGNANT_FIRST_TRIMESTER,
    PREGNANT_SECOND_TRIMESTER,
    PREGNANT_THIRD_TRIMESTER,
    UNKNOWN;

    @JsonCreator
    public static PregnancyStatus from(String value) {
        return PregnancyStatus.valueOf(value.toUpperCase());
    }
}
