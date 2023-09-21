package com.portfolio.housekeeping.DTOs;

import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDto {

    @NotBlank
    private String name;
    private String observation;
    @NotNull
    private CleaningStatus cleaningStatus;
    @NotNull
    private OccupationStatus occupationStatus;
}
