package com.portfolio.housekeeping.resource;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.housekeeping.dtos.AccommodationDto;
import com.portfolio.housekeeping.dtos.ExtraServicesDto;
import com.portfolio.housekeeping.dtos.PartialAccommodationDto;
import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.ExtraServices;
import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import com.portfolio.housekeeping.resource.exceptions.ResourceExceptionHandler;
import com.portfolio.housekeeping.services.AccommodationServ;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
class AccommodationResourceTest {

    private MockMvc mvc;

    @InjectMocks
    private AccommodationResource accommodationResource;
    @Mock
    private AccommodationServ accommodationServ;
    private List<Accommodation> accommodationList;
    private AccommodationDto validAccommodationDto;
    private AccommodationDto invalidAccommodationDto;
    private ExtraServicesDto extraServicesDto;
    private PartialAccommodationDto partialAccommodationDto;


    private JacksonTester<AccommodationDto> jsonReservationDto;
    private JacksonTester<ExtraServicesDto> jsonExtraServicesDto;
    private JacksonTester<PartialAccommodationDto> jsonPartialDto;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper().registerModule(new JavaTimeModule()));

        mvc = MockMvcBuilders.standaloneSetup(accommodationResource)
                .setControllerAdvice(new ResourceExceptionHandler())
                .build();

        createAccommodationObjectsInList();
        createAccommodationDto();
        createExtraServicesDto();
        createPartialAccommodationDto();
    }

    @DisplayName("Deve ser capaz de listar as acomodações")
    @Test
    void shouldBeAbleToListAllAccommodations() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findAllAccommodations()).thenReturn(accommodationList);

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/accommodations/get/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        List<Accommodation> responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Accommodation>>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotEmpty()
                .isEqualTo(accommodationList);
    }

    @Test
    void shouldNotBeAbleToListAllAccommodationsWhenListIsEmpty() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findAllAccommodations()).thenReturn(Collections.emptyList());

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/accommodations/get/all")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Deve ser capaz de encontrar uma acomodação pelo Id")
    @Test
    void shouldBeAbleToFindAccommodationById() throws Exception {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        Mockito.when(accommodationServ.findById(1L)).thenReturn(Optional.of(accTest));

        //Action
        MockHttpServletResponse response = mvc.perform(
                get("/accommodations/get/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Accommodation responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<Accommodation>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotNull()
                .isEqualTo(accTest);
    }

    @DisplayName("Não deve ser capaz de encontrar uma acomodação pelo id quando id não existir")
    @Test
    void shouldNotBeAbleToFindAccommodationByIdWhenIdDontExist() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                get("/accommodations/get/4")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Deve ser capaz de efetuar POST de uma acomodação")
    @Test
    void shouldBeAbleToSaveAccommodation() throws Exception {
        //Assemble
        Accommodation accommodation = accommodationList.get(0);
        Mockito.when(accommodationServ.saveAccommodation(Mockito.any(Accommodation.class))).thenReturn(accommodation);

        //Action
        MockHttpServletResponse response = mvc.perform(
                post("/accommodations/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.CREATED.value());
        Accommodation responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<Accommodation>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotNull()
                .isEqualTo(accommodation);
    }

    @DisplayName("Não deve ser capaz de efetuar um POST quando for uma dto invalida")
    @Test
    void shouldNotBeAbleToSaveAccommodationWhenItsInvalidDTO() throws Exception {
        //Assemble
        MockHttpServletResponse response = mvc.perform(
                post("/accommodations/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(invalidAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
    }

    @DisplayName("Deve ser capaz de fazer um PUT completo de uma acomodação")
    @Test
    void shouldBeAbleToFullPUTAccommodationById() throws Exception {
        //Assemble
        Accommodation accTest = accommodationList.get(1);
        Mockito.when(accommodationServ.findById(2L)).thenReturn(Optional.of(accTest));
        Mockito.when(accommodationServ.saveAccommodation(Mockito.any(Accommodation.class))).thenReturn(accommodationList.get(0));

        //Action
        MockHttpServletResponse response = mvc.perform(
                put("/accommodations/edit/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Accommodation responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<Accommodation>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotNull()
                .isEqualTo(accommodationList.get(0));
    }

    @DisplayName("Não deve ser capaz de fazer um PUT completo quando o Id não puder ser encontrado")
    @Test
    void shouldNotBeAbleToFullPUTAccommodationWhenIdCanNotBeFounded() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findById(5L)).thenReturn(Optional.empty());

        //Action
        MockHttpServletResponse response = mvc.perform(
                put("/accommodations/edit/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReservationDto.write(validAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }

    @DisplayName("Deve ser capaz de efetuar um PATCH para atualizar o Objeto ExtraServices relacionado a Acomodação")
    @Test
    void shouldBeAbleToPATCHAccommodationExtraServices() throws Exception {
        //Assemble
        Accommodation accTest = accommodationList.get(2);
        Mockito.when(accommodationServ.findById(3L)).thenReturn(Optional.of(accTest));
        Mockito.when(accommodationServ.saveAccommodation(Mockito.any(Accommodation.class))).thenReturn(accTest);

        //Action
        MockHttpServletResponse response = mvc.perform(
                patch("/accommodations/edit/extra/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExtraServicesDto.write(extraServicesDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Accommodation responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<Accommodation>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("name", "Chale 03");
    }

    @DisplayName("Não deve ser capaz de efetuar um PATCH do Objeto ExtraServices quando Accommodation não puder ser encontrada pelo id")
    @Test
    void shouldNotBeAbleToPATCHAccommodationWhenAccommodationCantBeFoundById() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findById(5L)).thenReturn(Optional.empty());

        //Action
        MockHttpServletResponse response = mvc.perform(
                patch("/accommodations/edit/extra/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonExtraServicesDto.write(extraServicesDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }

    @DisplayName("Deve ser capaz de efetuar PATCH de informações parciais da acomodação")
    @Test
    void shouldBeAbleToPATCHPartialInformationFromAccommodation() throws Exception {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        Mockito.when(accommodationServ.findById(1L)).thenReturn(Optional.of(accTest));
        Mockito.when(accommodationServ.saveAccommodation(Mockito.any(Accommodation.class))).thenReturn(accTest);

        //Action
        MockHttpServletResponse response = mvc.perform(
                patch("/accommodations/edit/obs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPartialDto.write(partialAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Accommodation responseAcc = objectMapper.readValue(response.getContentAsString(), new TypeReference<Accommodation>() {
        });
        Assertions.assertThat(responseAcc)
                .isNotNull()
                .isEqualTo(accTest);
    }

    @DisplayName("Não deve ser capaz de efetuar PATCH de informações parciais quando Id da acomodação não existir")
    @Test
    void shouldNotBeAbleToPATCHPartialInformationFromAccommodationWhenAccommodationIdDontExist() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findById(5L)).thenReturn(Optional.empty());

        //Actions
        MockHttpServletResponse response = mvc.perform(
                patch("/accommodations/edit/obs/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPartialDto.write(partialAccommodationDto).getJson())
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }

    @DisplayName("Deve ser capaz de deletar uma acomodação pelo id")
    @Test
    void shouldBeAbleToDeleteAccommodationById() throws Exception {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        Mockito.when(accommodationServ.findById(1L)).thenReturn(Optional.of(accTest));

        //Action
        MockHttpServletResponse response = mvc.perform(
                delete("/accommodations/delete/1")
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verify(accommodationServ).deleteAccommodation(1L);
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }

    @DisplayName("Não deve ser capaz de deletar acomodação quando Id não existir")
    @Test
    void shouldNotBeAbleToDeleteAccommodationWhenIdDoesntExist() throws Exception {
        //Assemble
        Mockito.when(accommodationServ.findById(5L)).thenReturn(Optional.empty());

        //Action
        MockHttpServletResponse response = mvc.perform(
                delete("/accommodations/delete/5")
        ).andReturn().getResponse();

        //Assertions
        Assertions.assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString())
                .isEmpty();
        Mockito.verifyNoMoreInteractions(accommodationServ);
    }


    private void createAccommodationObjectsInList() {
        Accommodation accommodation1 = new Accommodation(1L, "Chale 01", "Enxame de vespas"
                , CleaningStatus.LIMPO, OccupationStatus.MANUTENCAO,
                null, null);
        Accommodation accommodation2 = new Accommodation(2L, "Chale 02", ""
                , CleaningStatus.SUJO, OccupationStatus.OCUPADO,
                null, new ExtraServices());
        Accommodation accommodation3 = new Accommodation(3L, "Chale 03", "Infiltracao por todo banheiro"
                , CleaningStatus.SUJO, OccupationStatus.OCUPADO,
                null, new ExtraServices(3L, false, true, true, null));

        accommodationList = new ArrayList<>();
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        accommodationList.add(accommodation3);
    }

    private void createAccommodationDto() {
        validAccommodationDto = new AccommodationDto("Chale 01", "Enxame de vespas", CleaningStatus.LIMPO, OccupationStatus.MANUTENCAO);
        invalidAccommodationDto = new AccommodationDto(null, "", CleaningStatus.SUJO, OccupationStatus.OCUPADO);
    }

    private void createExtraServicesDto() {
        extraServicesDto = new ExtraServicesDto(3L,false, true, true);
    }

    private void createPartialAccommodationDto() {
        partialAccommodationDto = new PartialAccommodationDto("Ninho de passarionho na janela", CleaningStatus.SUJO, OccupationStatus.MANUTENCAO);
    }

}