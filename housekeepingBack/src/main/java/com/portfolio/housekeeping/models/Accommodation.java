package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_accommodation")
@Data
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

    public Accommodation(Long id, String name, String observation, CleaningStatus cleaningStatus, OccupationStatus occupationStatus, List<Reservation> reservation, ExtraServices extraServices) {
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

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", observation='" + observation + '\'' +
                ", cleaningStatus=" + cleaningStatus +
                ", occupationStatus=" + occupationStatus +
                '}';
    }
}
