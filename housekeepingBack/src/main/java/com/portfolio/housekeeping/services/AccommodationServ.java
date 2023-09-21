package com.portfolio.housekeeping.services;

import com.portfolio.housekeeping.models.Accommodation;
import com.portfolio.housekeeping.repositories.AccommodationRepo;
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
public class AccommodationServ {

    @Autowired
    private final AccommodationRepo accommodationRepo;

    public List<Accommodation> findAllAccommodations() {
        List<Accommodation> accommodations = accommodationRepo.findAll();

        if (accommodations.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return accommodations;
    }

    public Optional<Accommodation> findById(Long id) {
        Optional<Accommodation> optional = accommodationRepo.findById(id);

        return Optional.ofNullable(optional.orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Transactional
    public Accommodation saveAccommodation(Accommodation accommodation) {

        if (accommodation.getName().isEmpty()) {
            throw new IllegalArgException("O nome não pode estar vazio!");
        }

        return accommodationRepo.save(accommodation);
    }

    @Transactional
    public void deleteAccommodation(Long id) {
        Optional<Accommodation> accommodation = findById(id);

        accommodationRepo.delete(accommodation.get());
    }
}
