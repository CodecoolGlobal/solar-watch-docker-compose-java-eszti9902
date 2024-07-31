package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
    City findByCityId(Long cityId);
    void deleteByCityId(Long cityId);
}
