package com.portfolio.housekeeping.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class ReservationDto {

    @NotBlank
    private String holderName;
    @NotNull
    private int adults;
    @NotNull
    private int child;
    @NotNull
    private int babies;
    @NotNull
    private Boolean hasPet;
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;
    @NotNull
    private Long accommodationIdReference;
}
