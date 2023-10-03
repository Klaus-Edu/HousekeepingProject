package com.portfolio.housekeeping.resource;

import com.portfolio.housekeeping.dtos.AccommodationDto;
import com.portfolio.housekeeping.dtos.ExtraServicesDto;
import com.portfolio.housekeeping.dtos.PartialAccommodationDto;
import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.models.ExtraServices;
import com.portfolio.housekeeping.services.AccommodationServ;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accommodations")
public class AccommodationResource {

    @Autowired
    private final AccommodationServ accommodationServ;

    public AccommodationResource(AccommodationServ accommodationServ) {
        this.accommodationServ = accommodationServ;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Accommodation>> findAll() {
        List<Accommodation> list = accommodationServ.findAllAccommodations();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Accommodation> findById(@PathVariable(value = "id") Long id) {
        Optional<Accommodation> accommodation = accommodationServ.findById(id);

        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAccommodation(@RequestBody @Valid AccommodationDto accommodationDto) {
        Accommodation accommodation = new Accommodation(null,
                accommodationDto.getName(),
                accommodationDto.getObservation(),
                accommodationDto.getCleaningStatus(),
                accommodationDto.getOccupationStatus(),
                null,
                null
        );

        ExtraServices extraServices = new ExtraServices(null, false, false, false, accommodation);
        accommodation.setExtraServices(extraServices);

        return new ResponseEntity<>(accommodationServ.saveAccommodation(accommodation), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editAccommodation(@PathVariable(value = "id") Long id, @RequestBody @Valid AccommodationDto accommodationDto) {

        Optional<Accommodation> optional = accommodationServ.findById(id);

        if (optional.isPresent()) {
            Accommodation accommodation = optional.get();

            accommodation.setName(accommodationDto.getName());
            accommodation.setObservation(accommodationDto.getObservation());
            accommodation.setCleaningStatus(accommodationDto.getCleaningStatus());
            accommodation.setOccupationStatus(accommodationDto.getOccupationStatus());
            accommodation.setId(optional.get().getId());

            return new ResponseEntity<>(accommodationServ.saveAccommodation(accommodation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/edit/extra/{id}")
    public ResponseEntity<Object> editExtras(@PathVariable(value = "id") Long id, @RequestBody @Valid ExtraServicesDto extraServicesDto) {
        Optional<Accommodation> optional = accommodationServ.findById(id);

        if (optional.isPresent()) {
            Accommodation accommodation = optional.get();
            ExtraServices services = new ExtraServices(id, extraServicesDto.getExtraCleaning(), extraServicesDto.getExtraBed(), extraServicesDto.getExtraCradle(), accommodation);
            accommodation.setExtraServices(services);

            return new ResponseEntity<>(accommodationServ.saveAccommodation(accommodation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/edit/obs/{id}")
    public ResponseEntity<Object> editObservations(@PathVariable(value = "id") Long id, @RequestBody @Valid PartialAccommodationDto partialDto) {
        Optional<Accommodation> optional = accommodationServ.findById(id);

        if (optional.isPresent()) {
            Accommodation accommodation = optional.get();
            accommodation.setObservation(partialDto.getObservation());
            accommodation.setOccupationStatus(partialDto.getOccupationStatus());
            accommodation.setCleaningStatus(partialDto.getCleaningStatus());
            accommodation.setId(id);

            return new ResponseEntity<>(accommodationServ.saveAccommodation(accommodation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAccommodation(@PathVariable(value = "id") Long id) {
        Optional<Accommodation> accommodation = accommodationServ.findById(id);

        if (accommodation.isPresent()) {
            accommodationServ.deleteAccommodation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
