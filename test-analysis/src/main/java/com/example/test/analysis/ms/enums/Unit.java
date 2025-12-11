package com.example.test.analysis.ms.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Unit {
    U_L,
    MG_DL,
    MMOL_L,
    PERCENT,
    mg_dl,
    mIU_L,
    OTHER;


//    @JsonCreator
//    public static Unit from(String value) {
//        return Unit.valueOf(value.toUpperCase());
//    }

    @JsonCreator
    public static Unit fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Unit.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
