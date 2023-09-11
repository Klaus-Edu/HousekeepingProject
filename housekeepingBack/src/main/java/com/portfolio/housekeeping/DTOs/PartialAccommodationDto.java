package com.portfolio.housekeeping.DTOs;

import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartialAccommodationDto {

    private String observation;
    @NotNull
    private CleaningStatus cleaningStatus;
    @NotNull
    private OccupationStatus occupationStatus;
}
