package com.portfolio.housekeeping.dtos;

import jakarta.validation.constraints.NotNull;

public class ExtraServicesDto {

    @NotNull
    private Long id;
    @NotNull
    private Boolean extraCleaning;
    @NotNull
    private Boolean extraBed;
    @NotNull
    private Boolean extraCradle;

    public ExtraServicesDto(){};

    public ExtraServicesDto(Long id, Boolean extraCleaning, Boolean extraBed, Boolean extraCradle) {
        this.id = id;
        this.extraCleaning = extraCleaning;
        this.extraBed = extraBed;
        this.extraCradle = extraCradle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExtraCleaning() {
        return extraCleaning;
    }

    public void setExtraCleaning(Boolean extraCleaning) {
        this.extraCleaning = extraCleaning;
    }

    public Boolean getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(Boolean extraBed) {
        this.extraBed = extraBed;
    }

    public Boolean getExtraCradle() {
        return extraCradle;
    }

    public void setExtraCradle(Boolean extraCradle) {
        this.extraCradle = extraCradle;
    }
}
