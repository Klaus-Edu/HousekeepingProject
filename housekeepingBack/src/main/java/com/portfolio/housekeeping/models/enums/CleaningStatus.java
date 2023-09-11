package com.portfolio.housekeeping.models.enums;

public enum CleaningStatus {

    SUJO(1),
    LIMPO(2);

    private int code;

    private CleaningStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static CleaningStatus valueOf(int code){
        for(CleaningStatus value : CleaningStatus.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid CleaningStatus code");
    }
}
