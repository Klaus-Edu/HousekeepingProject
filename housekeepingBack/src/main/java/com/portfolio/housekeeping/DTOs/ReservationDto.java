package com.portfolio.housekeeping.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
