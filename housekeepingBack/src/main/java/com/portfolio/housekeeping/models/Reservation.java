package com.portfolio.housekeeping.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
