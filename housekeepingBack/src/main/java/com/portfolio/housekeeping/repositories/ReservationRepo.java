package com.portfolio.housekeeping.repositories;

import com.portfolio.housekeeping.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

}
