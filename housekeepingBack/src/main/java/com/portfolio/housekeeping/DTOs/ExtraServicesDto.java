package com.portfolio.housekeeping.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExtraServicesDto {

    @NotNull
    private Boolean extraCleaning;
    @NotNull
    private Boolean extraBed;
    @NotNull
    private Boolean extraCradle;
}
