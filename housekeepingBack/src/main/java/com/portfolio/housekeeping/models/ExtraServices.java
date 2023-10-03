package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_extra_services")
public class ExtraServices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean extraCleaning;
    private Boolean extraBed;
    private Boolean extraCradle;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Accommodation accommodation;

    public ExtraServices(){};

    public ExtraServices(Long id, Boolean extraCleaning, Boolean extraBed, Boolean extraCradle, Accommodation accommodation) {
        this.id = id;
        this.extraCleaning = extraCleaning;
        this.extraBed = extraBed;
        this.extraCradle = extraCradle;
        this.accommodation = accommodation;
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
        ExtraServices that = (ExtraServices) o;
        return Objects.equals(id, that.id) && Objects.equals(extraCleaning, that.extraCleaning) && Objects.equals(extraBed, that.extraBed) && Objects.equals(extraCradle, that.extraCradle) && Objects.equals(accommodation, that.accommodation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, extraCleaning, extraBed, extraCradle, accommodation);
    }
}
