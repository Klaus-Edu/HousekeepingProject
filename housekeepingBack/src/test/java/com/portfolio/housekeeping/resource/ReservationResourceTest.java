package com.portfolio.housekeeping.resource;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.housekeeping.DTOs.ReservationDto;
import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.ExtraServices;
import com.portfolio.housekeeping.models.Reservation;
import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import com.portfolio.housekeeping.resource.exceptions.ResourceExceptionHandler;
import com.portfolio.housekeeping.services.AccommodationServ;
import com.portfolio.housekeeping.services.ReservationServ;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
class ReservationResourceTest {

    private MockMvc mvc;

    @InjectMocks
    private ReservationResource reservationResource;
    @Mock
    private ReservationServ reservationServ;
    @Mock
    private AccommodationServ accommodationServ;
    private JacksonTester<ReservationDto> jsonReservationDto;
    private List<Reservation> reservationList;
    private ReservationDto validReservationDto;
    private Accommodation accommodation;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper().registerModule(new JavaTimeModule()));

        mvc = MockMvcBuilders.standaloneSetup(reservationResource)
                .setControllerAdvice(new ResourceExceptionHandler())
                .build();

        createReservationObjectsInList();
        createReservationDto();
        createAccommodation();
    }

    @DisplayName("Deve ser capaz de Listar as Reservas")
    @Test
    void shouldBeAbleToListAllReservations() throws Exception {
        //Assemble
        Mockito.when(reservationServ.findAllReservation()).thenReturn(reservationList);

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/reservation/get/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Reservation> responseReservations = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Reservation>>() {
        });
        Assertions.assertThat(responseReservations)
                .isNotEmpty()
                .isEqualTo(reservationList);

    }

    @DisplayName("Não deve ser capaz de listar as reservas quando a lista estiver vazia")
    @Test
    void shouldNotBeAbleToListAllReservationWhenReturnListIsEmpty() throws Exception {
        //Assemble
        Mockito.when(reservationServ.findAllReservation()).thenReturn(Collections.emptyList());

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/reservation/get/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Deve ser capaz de encontrar uma Reserva pelo Id")
    @Test
    void shouldBeAbleToFindReservationById() throws Exception {
        //Assemble
        Mockito.when(reservationServ.findById(1L)).thenReturn(Optional.of(reservationList.get(0)));

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/reservation/get/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Reservation reservationTest = objectMapper.readValue(response.getContentAsString(), new TypeReference<Reservation>() {
        });
        Assertions.assertThat(reservationTest)
                .isNotNull()
                .isEqualTo(reservationList.get(0));
    }

    @DisplayName("Não deve ser capaz de encontrar uma Reserva quando Id não existir")
    @Test
    void shouldNotBeAbleToFindReservationByIdWhenIdDoesNotExist() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                get("/reservations/get/3")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(response.getContentAsString()).isEmpty();
        Mockito.verifyNoInteractions(reservationServ);
    }

    @DisplayName("Deve ser capaz de efetuar um POST de uma reserva")
    @Test
    void shouldBeAbleToPostNewReservation() throws Exception {
        //Assemble
        Reservation reservation = reservationList.get(0);
        reservation.setId(null); //O Id é dado pelo banco de dados, na hora de criar precisa ser null;
        reservation.setAccommodation(accommodation);
        Mockito.when(accommodationServ.findById(1L)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationServ.saveReservation(reservation)).thenReturn(reservation);

        //Action
        MockHttpServletResponse response = mvc.perform(
                post("/reservation/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validReservationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
        Reservation reservationTest = objectMapper.readValue(response.getContentAsString(), new TypeReference<Reservation>() {
        });
        reservation.setAccommodation(null); //O restorno esperado do post é apenas a reserva, sem o objeto Accommodation
        Assertions.assertThat(reservationTest)
                .isNotNull()
                .isEqualTo(reservation);
    }

    @DisplayName("Não deve ser capaz de efetuar POST de uma reserva quando a Acomodação não poder ser encontrada pelo Id")
    @Test
    void shouldNotBeAbleToPostNewReservationWhenAccommodationIdDontExist() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                post("/reservation/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validReservationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoInteractions(reservationServ);
    }

    @DisplayName("Deve ser capaz de editar uma reserva")
    @Test
    void shouldBeAbleToEditReservation() throws Exception {
        //Assemble
        Reservation reservation = reservationList.get(0);
        Mockito.when(reservationServ.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.when(accommodationServ.findById(1L)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationServ.saveReservation(reservation)).thenReturn(reservation);

        //Action
        MockHttpServletResponse response = mvc.perform(
                put("/reservation/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validReservationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isNotNull();
        Reservation reservationTest = objectMapper.readValue(response.getContentAsString(), new TypeReference<Reservation>() {
        });
        reservation.setAccommodation(null); //O restorno esperado do post é apenas a reserva, sem o objeto Accommodation
        Assertions.assertThat(reservationTest)
                .isEqualTo(reservation);
    }

    @DisplayName("Não deve ser capaz de efeutar PUT em uma reserva quando o id da reseva não existir")
    @Test
    void shouldNotBeAbleToEditReservationWhenReservationCantBeFoundedById() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                put("/reservation/edit/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validReservationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Não deve ser capaz de efeutar PUT em uma reserva quando o id da acomodação não existir")
    @Test
    void shouldNotBeAbleToEditReservationWhenAccommodationCantBeFoundedById() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                put("/reservation/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validReservationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Deve ser capaz de deletar uma reserva pelo id")
    @Test
    void shouldBeAbleToDeleteReservationById() throws Exception {
        //Assemble
        Mockito.when(reservationServ.findById(1L)).thenReturn(Optional.of(reservationList.get(0)));

        //Action
        MockHttpServletResponse response = mvc.perform(
                delete("/reservation/delete/1")
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verify(reservationServ).deleteReservation(1L);
    }

    @DisplayName("Não deve ser capaz de deletar uma reserva quando id não for encontrado")
    @Test
    void shouldNotBeAbleToDeleteReservationWhenIdCantBeFound() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                delete("/reservation/delete/5")
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }

    private void createReservationObjectsInList() {
        Reservation reservation = new Reservation(1L
                , "Pedro Marcos"
                , 2, 3, 1, false
                , LocalDate.of(2023, 5, 15)
                , LocalDate.of(2023, 5, 18)
                , false, 1L, null);
        Reservation reservation1 = new Reservation();
        reservation1.setHolderName("Maria Joaquina");
        reservation1.setId(2L);

        reservationList = new ArrayList<>();
        reservationList.add(reservation);
        reservationList.add(reservation1);
    }

    private void createReservationDto() {
        validReservationDto = new ReservationDto("Pedro Marcos", 2, 3, 1, false
                , LocalDate.of(2023, 5, 15)
                , LocalDate.of(2023, 5, 18), 1L);
    }

    private void createAccommodation() {
        accommodation = new Accommodation(1L, "Chalé 1", "Enxame de abelhas"
                , CleaningStatus.LIMPO, OccupationStatus.MANUTENCAO, null, new ExtraServices());
    }


}