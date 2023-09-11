package com.portfolio.housekeeping.repositories;

import com.portfolio.housekeeping.models.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccommodationRepo extends JpaRepository<Accommodation, Long> {

}
