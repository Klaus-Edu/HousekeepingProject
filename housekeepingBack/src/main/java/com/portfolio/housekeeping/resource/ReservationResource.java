package com.portfolio.housekeeping.resource;

import com.portfolio.housekeeping.DTOs.ExtraServicesDto;
import com.portfolio.housekeeping.DTOs.ReservationDto;
import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.services.AccommodationServ;
import com.portfolio.housekeeping.services.ReservationServ;
import com.portfolio.housekeeping.services.exceptions.IllegalArgException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationResource {

    @Autowired
    private final ReservationServ reservationServ;

    @Autowired
    private final AccommodationServ accommodationServ;

    @GetMapping("/get/all")
    public ResponseEntity<List<Reservation>> findAll(){
        return new ResponseEntity<>(reservationServ.findAllReservation(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(reservationServ.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReservation(@RequestBody @Valid ReservationDto reservationDto){
        Accommodation accommodation = accommodationServ.findById(reservationDto.getAccommodationIdReference()).get();

        if(reservationDto.getCheckout().isBefore(reservationDto.getCheckin())){
            throw new IllegalArgException("Checkout must be after check-in date!");
        }

        Reservation reservation = new Reservation(
                null,
                reservationDto.getHolderName(),
                reservationDto.getAdults(),
                reservationDto.getChild(),
                reservationDto.getBabies(),
                reservationDto.getHasPet(),
                reservationDto.getCheckin(),
                reservationDto.getCheckout(),
                false,
                reservationDto.getAccommodationIdReference(),
                accommodation
        );

        return new ResponseEntity<>(reservationServ.saveReservation(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editReservation(@PathVariable(value = "id") Long id, @RequestBody @Valid ReservationDto reservationDto){
        Optional<Reservation> optional = reservationServ.findById(id);
        Accommodation accommodation = accommodationServ.findById(reservationDto.getAccommodationIdReference()).get();

        Reservation reservation = optional.get();

        reservation.setId(optional.get().getId());
        reservation.setHolderName(reservationDto.getHolderName());
        reservation.setAdults(reservationDto.getAdults());
        reservation.setChild(reservationDto.getChild());
        reservation.setBabies(reservationDto.getBabies());
        reservation.setHasPet(reservationDto.getHasPet());
        reservation.setCheckin(reservationDto.getCheckin());
        reservation.setCheckout(reservationDto.getCheckout());
        reservation.setAccommodationIdReference(reservationDto.getAccommodationIdReference());
        reservation.setAccommodation(accommodation);

        return new ResponseEntity<>(reservationServ.saveReservation(reservation), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable(value = "id") Long id){
        reservationServ.deleteReservation(id);
    }

}

