package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_extra_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
