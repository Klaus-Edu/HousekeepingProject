package com.portfolio.housekeeping.resource.jobs;

import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import com.portfolio.housekeeping.services.AccommodationServ;
import com.portfolio.housekeeping.services.ReservationServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CheckoutJob {

    @Autowired
    private final ReservationServ reservationServ;

    @Autowired
    private final AccommodationServ accommodationServ;

    public CheckoutJob(ReservationServ reservationServ, AccommodationServ accommodationServ) {
        this.reservationServ = reservationServ;
        this.accommodationServ = accommodationServ;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void inactiveReservation() {
        List<Reservation> reservationList = reservationServ.findAllReservation();

        for (Reservation rev : reservationList) {
            if (rev.getCheckout().equals(LocalDate.now())) {
                rev.setIsActive(false);
            }
        }
        reservationServ.saveListReservation(reservationList);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void activeReservation() {
        List<Reservation> reservationList = reservationServ.findAllReservation();

        for (Reservation rev : reservationList) {
            if (rev.getCheckin().equals(LocalDate.now())) {
                rev.setIsActive(true);
            }
        }
        reservationServ.saveListReservation(reservationList);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void occupationStatusActive() {
        List<Reservation> reservationList = reservationServ.findAllReservation();

        for (Reservation rev : reservationList) {
            if (rev.getIsActive().equals(true)) {
                Accommodation accommodation = rev.getAccommodation();
                accommodation.setOccupationStatus(OccupationStatus.OCUPADO);
                accommodationServ.saveAccommodation(accommodation);
            }
        }
    }
}
