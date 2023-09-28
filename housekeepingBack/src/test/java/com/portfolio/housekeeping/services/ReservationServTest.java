package com.portfolio.housekeeping.services;

import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.repositories.ReservationRepo;
import com.portfolio.housekeeping.services.exceptions.IllegalArgException;
import com.portfolio.housekeeping.services.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ReservationServTest {

    @InjectMocks
    private ReservationServ reservationServ;
    @Mock
    private ReservationRepo reservationRepo;

    private List<Reservation> reservationList;
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource not found!";


    @BeforeEach
    void setup() {
        Reservation reservation = new Reservation(1L
                , "Pedro Marcos"
                , 2, 3, 1, false
                , LocalDate.of(2023, 5, 15)
                , LocalDate.of(2023, 5, 18)
                , true, 1L, null);
        Reservation reservation1 = new Reservation();
        reservation1.setHolderName("Maria Joaquina");
        reservation1.setId(2L);

        reservationList = new ArrayList<>();
        reservationList.add(reservation);
        reservationList.add(reservation1);
    }


    @DisplayName("Deve ser capaz de Listar as Reservas")
    @Test
    void shouldBeAbleToFindAllReservation() {
        //Assemble
        Mockito.when(reservationRepo.findAll()).thenReturn(reservationList);
        Reservation testReservation = reservationList.get(0);

        //Action
        List<Reservation> allReservation = reservationServ.findAllReservation();

        //Assertions
        Assertions.assertThat(allReservation)
                .isNotEmpty()
                .isEqualTo(reservationList)
                .contains(testReservation);
        Assertions.assertThat(allReservation.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("holderName", "Pedro Marcos");
    }

    @DisplayName("Não deve ser capaz de Listar as Reservas quando a lista esta vazia")
    @Test
    void shouldNotBeAbleToFindAllReservationWhenIsEmpty() {
        //Assemble
        Mockito.when(reservationRepo.findAll()).thenReturn(Collections.emptyList());

        //Assertions
        Assertions.assertThatThrownBy(() -> reservationServ.findAllReservation())
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(RESOURCE_NOT_FOUND_MESSAGE);

    }

    @DisplayName("Deve ser capaz de encontrar uma reserva pelo Id")
    @Test
    void shouldBeAbleToFindAReservationById() {
        //Assemble
        Mockito.when(reservationRepo.findById(2L)).thenReturn(Optional.of(reservationList.get(1)));

        //Action
        Reservation testReservation = reservationServ.findById(2L).get();

        //Assertions
        Assertions.assertThat(testReservation)
                .isNotNull()
                .isInstanceOf(Reservation.class);
        Assertions.assertThat(testReservation)
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("holderName", "Maria Joaquina");
    }

    @DisplayName("Não deve ser capaz de encontrar a reserva pelo id quando id não existir")
    @Test
    void shouldNotBeAbleToFindReservationByIdWhenIdMismatch() {
        //Assemble
        Mockito.when(reservationRepo.findById(3L)).thenReturn(Optional.empty());

        //Assertions
        Assertions.assertThatThrownBy(() -> reservationServ.findById(3L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE + " Id 3")
                .hasNoSuppressedExceptions();
    }

    @DisplayName("Deve ser capaz de salvar uma reserva")
    @Test
    void shouldBeAbleToSaveReservation() {
        //Assemble
        Reservation reservation = reservationList.get(0);
        Mockito.when(reservationRepo.save(reservation)).thenReturn(reservation);

        //Action
        Reservation savedReservation = reservationServ.saveReservation(reservation);

        //Assertions
        Assertions.assertThat(savedReservation)
                .isNotNull()
                .isInstanceOf(Reservation.class);
        Assertions.assertThat(savedReservation)
                .isEqualTo(reservation)
                .hasFieldOrPropertyWithValue("holderName", "Pedro Marcos")
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @DisplayName("Não deve ser capaz de salvar a reserva caso data de checkin for antes que checkout")
    @Test
    void shouldNotBeAbleToSaveReservationWhenCheckinDateIsBeforeCheckoutDate() {
        //Assemble
        Reservation reservation = reservationList.get(0);
        reservation.setCheckout(LocalDate.of(2023, 5, 14));

        //Assertions
        Assertions.assertThatThrownBy(() -> reservationServ.saveReservation(reservation))
                .isInstanceOf(IllegalArgException.class);
    }

    @DisplayName("Deve ser capaz de deletar uma reserva pelo Id")
    @Test
    void shouldBeAbleToDeleteReservationById() {
        //Assemble
        Reservation reservation = reservationList.get(0);
        Mockito.when(reservationRepo.findById(1L)).thenReturn(Optional.of(reservation));

        //Assertions
        Assertions.assertThatCode(() -> reservationServ.deleteReservation(1L))
                .doesNotThrowAnyException();
        Mockito.verify(reservationRepo).delete(any(Reservation.class));
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @DisplayName("Não deve ser capaz de deletar uma reserva quando o Id não for encontrado")
    @Test
    void shouldNotBeAbleToDeleteReservationByIdWhenIdObjectIsNotFound() {
        //Assemble
        Mockito.when(reservationRepo.findById(1L)).thenReturn(Optional.empty());

        //Assertions
        Assertions.assertThatThrownBy(() -> reservationServ.deleteReservation(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE + " Id 1");
        Mockito.verify(reservationRepo, never()).delete(any(Reservation.class));
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @DisplayName("Deve ser capaz de salvar uma Lista de Reservas")
    @Test
    void shouldBeAbleToSaveReservationList() {
        //Assertions
        Assertions.assertThatCode(() -> reservationServ.saveListReservation(reservationList))
                .doesNotThrowAnyException();
        Mockito.verify(reservationRepo).saveAll(reservationList);
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }

    @DisplayName("Não deve ser capaz de salvar uma lista de reservas quando a lista estiver vazia")
    @Test
    void shouldNotBeAbleToSaveReservationListWhenItsEmpty() {
        //Assemble
        List<Reservation> reservations = Collections.emptyList();

        Assertions.assertThatThrownBy(() -> reservationServ.saveListReservation(reservations))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE + " Id Reservation list is empty!");
        Mockito.verifyNoMoreInteractions(reservationRepo);
    }
}