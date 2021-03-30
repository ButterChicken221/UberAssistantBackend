package com.butterchicken.UberAssistant.enums;

import lombok.Getter;

@Getter
public enum CabType {
    GO("Uber Go", 0),
    BLACK("Uber Black", 1),
    SEDAN("Uber Sedan", 1),
    PRIME("Uber Prime", 1),
    AUTO("Uber Moto", 1);

    private final String cabTypeString;
    private final Integer cabTypeCode;

    CabType(String cabTypeString, Integer cabTypeCode) {
        this.cabTypeString = cabTypeString;
        this.cabTypeCode = cabTypeCode;
    }
}
