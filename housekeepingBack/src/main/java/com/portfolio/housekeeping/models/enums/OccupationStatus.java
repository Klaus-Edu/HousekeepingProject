package com.portfolio.housekeeping.models.enums;

public enum OccupationStatus {

    DESOCUPADO(1),
    OCUPADO(2),
    MANUTENCAO(3);

    private final int code;

    OccupationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OccupationStatus valueOf(int code) {
        for (OccupationStatus value : OccupationStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OccupationStatus code");
    }
}
