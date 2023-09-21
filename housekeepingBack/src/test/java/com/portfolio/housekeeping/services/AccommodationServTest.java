package com.portfolio.housekeeping.services;

import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.ExtraServices;
import com.portfolio.housekeeping.models.enums.CleaningStatus;
import com.portfolio.housekeeping.models.enums.OccupationStatus;
import com.portfolio.housekeeping.repositories.AccommodationRepo;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AccommodationServTest {

    @InjectMocks
    private AccommodationServ accommodationServ;
    @Mock
    private AccommodationRepo accommodationRepo;

    private List<Accommodation> accommodationList;
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource not found!";

    @BeforeEach
    void setUp() {
        Accommodation acc1 = new Accommodation(1L, "Chalé 1", "Enxame de abelhas"
                , CleaningStatus.LIMPO, OccupationStatus.MANUTENCAO, null, new ExtraServices());
        Accommodation acc7 = new Accommodation(7L, "Chalé 7", ""
                , CleaningStatus.LIMPO, OccupationStatus.DESOCUPADO, null, new ExtraServices());

        accommodationList = new ArrayList<>();
        accommodationList.add(acc1);
        accommodationList.add(acc7);
    }

    @DisplayName("Deve ser capaz de Listar as acomodações")
    @Test
    void shouldBeAbleToFindAllAccommodations() {
        //Assemble
        Mockito.when(accommodationRepo.findAll()).thenReturn(accommodationList);
        Accommodation accTest = accommodationList.get(0);

        //Action
        List<Accommodation> list = accommodationServ.findAllAccommodations();

        //Assertions
        Assertions.assertThat(list)
                .isNotEmpty()
                .isEqualTo(accommodationList)
                .contains(accTest);
        Assertions.assertThat(list.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("observation", "Enxame de abelhas");
        Assertions.assertThat(list.get(0).getCleaningStatus())
                .isInstanceOf(CleaningStatus.class);
        Assertions.assertThat(list.get(0).getOccupationStatus())
                .isInstanceOf(OccupationStatus.class);

    }

    @DisplayName("Não deve ser capaz de listar as acomodações quando a lista estiver vazia")
    @Test
    void shouldNotBeAbleToFindAllAccommodationWhenListIsEmpty() {
        //Assemble
        Mockito.when(accommodationRepo.findAll()).thenReturn(Collections.emptyList());

        //Assertions
        Assertions.assertThatThrownBy(() -> accommodationServ.findAllAccommodations())
                .isInstanceOf(ResourceNotFoundException.class).hasMessage(RESOURCE_NOT_FOUND_MESSAGE);
    }

    @DisplayName("Deve ser capaz de encontrar uma acomodação pelo id")
    @Test
    void shouldBeAbleToFindAccommodationById() {
        //Assemble
        Mockito.when(accommodationRepo.findById(1L)).thenReturn(Optional.of(accommodationList.get(0)));

        //Action
        Accommodation accTest = accommodationServ.findById(1L).get();

        //Assertions
        Assertions.assertThat(accTest)
                .isNotNull()
                .isInstanceOf(Accommodation.class);
        Assertions.assertThat(accTest)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Chalé 1");
    }

    @DisplayName("Não deve ser capaz de encontrar uma acomodação pelo ID quando ID não existir")
    @Test
    void shouldNotBeAbleToFindAccommodationByIdWhenMismatch() {
        //Assemble
        Mockito.when(accommodationRepo.findById(5L)).thenReturn(Optional.empty());

        //Assertions
        Assertions.assertThatThrownBy(() -> accommodationServ.findById(5L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE + " Id 5")
                .hasNoSuppressedExceptions();
    }

    @DisplayName("Deve ser capaz de salvar uma acomodação")
    @Test
    void shouldBeAbleToSaveAccommodation() {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        Mockito.when(accommodationRepo.save(accTest)).thenReturn(accTest);

        //Action
        Accommodation savedAccommodation = accommodationServ.saveAccommodation(accTest);

        //Assertions
        Assertions.assertThat(savedAccommodation)
                .isNotNull()
                .isInstanceOf(Accommodation.class);
        Assertions.assertThat(savedAccommodation)
                .isEqualTo(accTest)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Chalé 1");
    }

    @DisplayName("Não deve ser capaz de salvar uma Acomodação quando campo nome estiver vazio")
    @Test
    void shouldNotBeAbleToSaveAccommodationWhenNameFieldIsEmpty() {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        accTest.setName("");

        //Assertions
        Assertions.assertThatThrownBy(() -> accommodationServ.saveAccommodation(accTest))
                .isInstanceOf(IllegalArgException.class)
                .hasMessage("O nome não pode estar vazio!");
    }

    @DisplayName("Deve ser capaz de salvar uma acomodação")
    @Test
    void shouldBeAbleToDeleteAccommodationById() {
        //Assemble
        Accommodation accTest = accommodationList.get(0);
        Mockito.when(accommodationRepo.findById(1L)).thenReturn(Optional.of(accTest));

        //Assertions
        Assertions.assertThatCode(() -> accommodationServ.deleteAccommodation(1L))
                .doesNotThrowAnyException();
        Mockito.verify(accommodationRepo).delete(any(Accommodation.class));
        Mockito.verifyNoMoreInteractions(accommodationRepo);
    }

    @DisplayName("Não deve ser capaz de deletar uma acomodação quando Id não for encontrado")
    @Test
    void shouldNotBeAbleToDeleteAccommodationByIdWhenIdObjectIsNotFound() {
        //Assemble
        Mockito.when(accommodationRepo.findById(1L)).thenReturn(Optional.empty());

        //Assertions
        Assertions.assertThatThrownBy(() -> accommodationServ.deleteAccommodation(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(RESOURCE_NOT_FOUND_MESSAGE + " Id 1");
        Mockito.verify(accommodationRepo, never()).delete(any(Accommodation.class));
        Mockito.verifyNoMoreInteractions(accommodationRepo);
    }

}