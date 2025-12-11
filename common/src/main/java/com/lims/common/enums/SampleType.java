package com.lims.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SampleType {
    BLOOD,
    SERUM,
    PLASMA,
    TISSUE,
    FECES,
    URINE;

//    @JsonCreator
//    public static SampleType from(String value) {
//        return SampleType.valueOf(value.toUpperCase());
//    }


    @JsonCreator
    public static SampleType fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return SampleType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
