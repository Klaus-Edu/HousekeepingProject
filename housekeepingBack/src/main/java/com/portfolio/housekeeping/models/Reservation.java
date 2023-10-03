package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String holderName;
    private int adults;
    private int child;
    private int babies;
    private Boolean hasPet;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkout;
    private Boolean isActive;
    private Long accommodationIdReference;

    @ManyToOne
    @JsonBackReference
    private Accommodation accommodation;

    public  Reservation(){};

    public Reservation(Long id, String holderName, int adults, int child, int babies, Boolean hasPet, LocalDate checkin, LocalDate checkout, Boolean isActive, Long accommodationIdReference, Accommodation accommodation) {
        this.id = id;
        this.holderName = holderName;
        this.adults = adults;
        this.child = child;
        this.babies = babies;
        this.hasPet = hasPet;
        this.checkin = checkin;
        this.checkout = checkout;
        this.isActive = isActive;
        this.accommodationIdReference = accommodationIdReference;
        this.accommodation = accommodation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Long getAccommodationIdReference() {
        return accommodationIdReference;
    }

    public void setAccommodationIdReference(Long accommodationIdReference) {
        this.accommodationIdReference = accommodationIdReference;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return adults == that.adults && child == that.child && babies == that.babies && Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && Objects.equals(hasPet, that.hasPet) && Objects.equals(checkin, that.checkin) && Objects.equals(checkout, that.checkout) && Objects.equals(isActive, that.isActive) && Objects.equals(accommodationIdReference, that.accommodationIdReference) && Objects.equals(accommodation, that.accommodation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, adults, child, babies, hasPet, checkin, checkout, isActive, accommodationIdReference, accommodation);
    }
}
