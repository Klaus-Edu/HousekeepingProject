package com.portfolio.housekeeping.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraServicesDto {

    @NotNull
    private Boolean extraCleaning;
    @NotNull
    private Boolean extraBed;
    @NotNull
    private Boolean extraCradle;
}
