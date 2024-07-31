package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SunriseSunsetRepository extends JpaRepository<SunriseSunset, Long> {
    Optional<SunriseSunset> findByCityNameAndDate(String cityName, String date);
    List<SunriseSunset> findAllByCity_CityId(long id);
}
