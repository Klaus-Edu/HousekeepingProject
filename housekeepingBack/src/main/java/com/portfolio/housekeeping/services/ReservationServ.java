package com.portfolio.housekeeping.services;

import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.repositories.ReservationRepo;
import com.portfolio.housekeeping.services.exceptions.IllegalArgException;
import com.portfolio.housekeeping.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationServ {

    @Autowired
    private final ReservationRepo reservationRepo;

    public List<Reservation> findAllReservation() {
        List<Reservation> reservations = reservationRepo.findAll();

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reservations;
    }

    public Optional<Reservation> findById(Long id) {
        Optional<Reservation> optional = reservationRepo.findById(id);

        return Optional.ofNullable(optional.orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    ;

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        if (reservation.getCheckout().isBefore(reservation.getCheckin())) {
            throw new IllegalArgException("Checkout must be after check-in date!");
        }
        return reservationRepo.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Optional<Reservation> reservation = findById(id);

        reservationRepo.delete(reservation.get());
    }

    public void saveListReservation(List<Reservation> list) {
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Reservation list is empty!");
        }

        reservationRepo.saveAll(list);
    }

}
