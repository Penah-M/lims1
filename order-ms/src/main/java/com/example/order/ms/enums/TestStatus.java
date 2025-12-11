package com.example.order.ms.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TestStatus {
    ACTIVE,
    INACTIVE,
    DELETE,
    DRAFT;

    @JsonCreator
    public static TestStatus fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return TestStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


}
