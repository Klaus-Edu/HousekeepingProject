package com.portfolio.housekeeping.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

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

    public ReservationDto(){};

    public ReservationDto(String holderName, int adults, int child, int babies, Boolean hasPet, LocalDate checkin, LocalDate checkout, Long accommodationIdReference) {
        this.holderName = holderName;
        this.adults = adults;
        this.child = child;
        this.babies = babies;
        this.hasPet = hasPet;
        this.checkin = checkin;
        this.checkout = checkout;
        this.accommodationIdReference = accommodationIdReference;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getBabies() {
        return babies;
    }

    public void setBabies(int babies) {
        this.babies = babies;
    }

    public Boolean getHasPet() {
        return hasPet;
    }

    public void setHasPet(Boolean hasPet) {
        this.hasPet = hasPet;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public Long getAccommodationIdReference() {
        return accommodationIdReference;
    }

    public void setAccommodationIdReference(Long accommodationIdReference) {
        this.accommodationIdReference = accommodationIdReference;
    }
}
