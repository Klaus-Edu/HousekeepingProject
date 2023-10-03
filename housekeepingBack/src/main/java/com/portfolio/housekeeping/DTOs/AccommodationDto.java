package com.portfolio.housekeeping.dtos;

import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccommodationDto {

    @NotBlank
    private String name;
    private String observation;
    @NotNull
    private CleaningStatus cleaningStatus;
    @NotNull
    private OccupationStatus occupationStatus;

    public AccommodationDto(){};

    public AccommodationDto(String name, String observation, CleaningStatus cleaningStatus, OccupationStatus occupationStatus) {
        this.name = name;
        this.observation = observation;
        this.cleaningStatus = cleaningStatus;
        this.occupationStatus = occupationStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public CleaningStatus getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(CleaningStatus cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    public OccupationStatus getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(OccupationStatus occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

}
