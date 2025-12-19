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
    public static PregnancyStatus fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return PregnancyStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
