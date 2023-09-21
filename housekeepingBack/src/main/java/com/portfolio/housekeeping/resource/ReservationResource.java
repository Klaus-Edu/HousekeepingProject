package com.portfolio.housekeeping.resource;

import com.portfolio.housekeeping.DTOs.ReservationDto;
import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.services.AccommodationServ;
import com.portfolio.housekeeping.services.ReservationServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservationList = reservationServ.findAllReservation();

        if (reservationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reservationList, HttpStatus.OK);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable(value = "id") Long id) {
        Optional<Reservation> reservation = reservationServ.findById(id);

        return reservation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReservation(@RequestBody @Valid ReservationDto reservationDto) {
        Optional<Accommodation> accommodation = accommodationServ.findById(reservationDto.getAccommodationIdReference());

        if (accommodation.isPresent()) {
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
                    accommodation.get()
            );

            return new ResponseEntity<>(reservationServ.saveReservation(reservation), HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editReservation(@PathVariable(value = "id") Long id, @RequestBody @Valid ReservationDto reservationDto) {
        Optional<Reservation> reservationOptional = reservationServ.findById(id);
        Optional<Accommodation> accommodation = accommodationServ.findById(reservationDto.getAccommodationIdReference());

        if (reservationOptional.isPresent() && accommodation.isPresent()) {
            Reservation reservation = reservationOptional.get();

            reservation.setId(reservationOptional.get().getId());
            reservation.setHolderName(reservationDto.getHolderName());
            reservation.setAdults(reservationDto.getAdults());
            reservation.setChild(reservationDto.getChild());
            reservation.setBabies(reservationDto.getBabies());
            reservation.setHasPet(reservationDto.getHasPet());
            reservation.setCheckin(reservationDto.getCheckin());
            reservation.setCheckout(reservationDto.getCheckout());
            reservation.setAccommodationIdReference(reservationDto.getAccommodationIdReference());
            reservation.setAccommodation(accommodation.get());

            return new ResponseEntity<>(reservationServ.saveReservation(reservation), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable(value = "id") Long id) {
        Optional<Reservation> reservation = reservationServ.findById(id);

        if (reservation.isPresent()) {
            reservationServ.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

