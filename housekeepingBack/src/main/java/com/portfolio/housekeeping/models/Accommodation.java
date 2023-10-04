package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_accommodation")
public class Accommodation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String observation;
    private Integer cleaningStatus;
    private Integer occupationStatus;

    @OneToMany(mappedBy = "accommodation")
    @JsonManagedReference
    private List<Reservation> reservation = new ArrayList<>();

    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private ExtraServices extraServices;
    public Accommodation() {

    }

    public Accommodation(Long id, String name, String observation,
                         CleaningStatus cleaningStatus, OccupationStatus occupationStatus,
                         List<Reservation> reservation, ExtraServices extraServices) {
        this.id = id;
        this.name = name;
        this.observation = observation;
        setCleaningStatus(cleaningStatus);
        setOccupationStatus(occupationStatus);
        this.reservation = reservation;
        this.extraServices = extraServices;
    }

    public CleaningStatus getCleaningStatus() {
        return CleaningStatus.valueOf(cleaningStatus);
    }

    public void setCleaningStatus(CleaningStatus cleaningStatus) {
        if (cleaningStatus != null) {
            this.cleaningStatus = cleaningStatus.getCode();
        }
    }

    public OccupationStatus getOccupationStatus() {
        return OccupationStatus.valueOf(occupationStatus);
    }

    public void setOccupationStatus(OccupationStatus occupationStatus) {
        if (occupationStatus != null) {
            this.occupationStatus = occupationStatus.getCode();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Reservation> getReservation() {
        return reservation;
    }

    public ExtraServices getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(ExtraServices extraServices) {
        this.extraServices = extraServices;
    }

    @Override
    public String toString() {
        return "Accommodation{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", observation='" + observation + '\''
                + ", cleaningStatus=" + cleaningStatus
                + ", occupationStatus=" + occupationStatus
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(observation, that.observation) && Objects.equals(cleaningStatus, that.cleaningStatus) && Objects.equals(occupationStatus, that.occupationStatus) && Objects.equals(reservation, that.reservation) && Objects.equals(extraServices, that.extraServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, observation, cleaningStatus, occupationStatus, reservation, extraServices);
    }
}
