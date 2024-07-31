package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.WrongUrlException;
import com.codecool.solarwatch.exception.DataNotInDatabaseException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OpenSolarWatchService {
    private static final String API_KEY = "38589fe57a915da2f0cb9e3e58814320";
    private static final Logger logger = LoggerFactory.getLogger(OpenSolarWatchService.class);
    private final CityRepository cityRepository;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OpenSolarWatchService(RestTemplate restTemplate, CityRepository cityRepository, SunriseSunsetRepository sunriseSunsetRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

//    public SolarReport getCurrentSolarReportForGivenCity(String city, String date) throws WrongUrlException {
//        String coordinatesUrl = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", city, API_KEY);
//        LatitudeLongitudeReport[] coordinateResponse = restTemplate.getForObject(coordinatesUrl, LatitudeLongitudeReport[].class);
//        assert coordinateResponse != null;
//        if (coordinateResponse.length == 0) {
//            throw new WrongUrlException();
//        }
//        String solarUrl = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&date=%s", coordinateResponse[0].lat(), coordinateResponse[0].lon(), date);
//        OpenSolarReport solarReportResponse = restTemplate.getForObject(solarUrl, OpenSolarReport.class);
//        assert solarReportResponse != null;
//        logger.info("Response from API: {}", solarReportResponse);
//        return new SolarReport(city, coordinateResponse[0].lat(), coordinateResponse[0].lon(), date, solarReportResponse.results().sunrise(), solarReportResponse.results().sunset());
//    }

    public List<SolarReport> getAllSolarReport() {
        List<City> cities = cityRepository.findAll();
        List<SunriseSunset> sunriseSunsets = sunriseSunsetRepository.findAll();
        List<SolarReport> solarReports = new ArrayList<>();

        for (SunriseSunset sunriseSunset : sunriseSunsets) {
            for (City city : cities) {
                if (sunriseSunset.getCityName().equals(city.getCityName())) {
                    SolarReport solarReport = new SolarReport(city.getCityId(), city.getCityName(), city.getLatitude(), city.getLongitude(), sunriseSunset.getDate(), sunriseSunset.getSunrise(), sunriseSunset.getSunset());
                    solarReports.add(solarReport);
                }
            }
        }
        return solarReports;
    }

    public SolarReport getDatabaseSolarReport(String cityName, String date) {
        Optional<City> dbCity = getCityFromDatabase(cityName);
        Optional<SunriseSunset> dbSunriseSunset = getSunriseSunsetFromDatabase(cityName, date);
        return new SolarReport(dbCity.get().getCityId() ,cityName, dbCity.get().getLatitude(), dbCity.get().getLongitude(), date, dbSunriseSunset.get().getSunrise(), dbSunriseSunset.get().getSunset());
    }


    private Optional<City> getCityFromDatabase(String cityName) {
        Optional<City> dbCity = cityRepository.findByCityName(cityName);

        if (dbCity.isEmpty()) {
            throw new DataNotInDatabaseException();
        }
        return dbCity;
    }

    private Optional<SunriseSunset> getSunriseSunsetFromDatabase(String cityName, String date) {
        Optional<SunriseSunset> dbSunriseSunset = sunriseSunsetRepository.findByCityNameAndDate(cityName, date);

        if (dbSunriseSunset.isEmpty()) {
            throw new DataNotInDatabaseException();
        }
        return dbSunriseSunset;
    }

    public SolarReport getSolarReportFor(String cityName, String date) throws WrongUrlException {
        City city = getCoordinateByCityName(cityName);
        SunriseSunset sunriseSunset = getSunriseSunsetByLatitudeLongitude(city, date);
        return new SolarReport(city.getCityId(), cityName, city.getLatitude(), city.getLongitude(), date, sunriseSunset.getSunrise(), sunriseSunset.getSunset());
    }

    public CityReport getCityReportByCityId(long cityId) {
        City city = cityRepository.findByCityId(cityId);
        return new CityReport(city.getCityName());
    }

    private City getCoordinateByCityName(String city) throws WrongUrlException {
        Optional<City> dbCity = cityRepository.findByCityName(city);

        if (dbCity.isPresent()) {
            return dbCity.get();
        }
        String coordinatesUrl = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", city, API_KEY);
        LatitudeLongitudeReport[] coordinateResponse = restTemplate.getForObject(coordinatesUrl, LatitudeLongitudeReport[].class);
        assert coordinateResponse != null;
        if (coordinateResponse.length == 0) {
            throw new WrongUrlException();
        }
        City fetchedCity = new City();
        fetchedCity.setCityName(coordinateResponse[0].name());
        fetchedCity.setLatitude(coordinateResponse[0].lat());
        fetchedCity.setLongitude(coordinateResponse[0].lon());
        fetchedCity.setCountry(coordinateResponse[0].country());
        fetchedCity.setState(coordinateResponse[0].state());
        cityRepository.save(fetchedCity);
        return fetchedCity;
    }

    private SunriseSunset getSunriseSunsetByLatitudeLongitude(City city, String date) {
        Optional<SunriseSunset> dbSunriseSunset = sunriseSunsetRepository.findByCityNameAndDate(city.getCityName(), date);

        if (dbSunriseSunset.isPresent()) {
            return dbSunriseSunset.get();
        }
        String solarUrl = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&date=%s", city.getLatitude(), city.getLongitude(), date);
        OpenSolarReport solarReportResponse = restTemplate.getForObject(solarUrl, OpenSolarReport.class);
        assert solarReportResponse != null;

        SunriseSunset fetchedSunriseSunset = new SunriseSunset();
        fetchedSunriseSunset.setCityName(city.getCityName());
        fetchedSunriseSunset.setDate(date);
        fetchedSunriseSunset.setSunrise(solarReportResponse.results().sunrise());
        fetchedSunriseSunset.setSunset(solarReportResponse.results().sunset());
        fetchedSunriseSunset.setCity(city);
        sunriseSunsetRepository.save(fetchedSunriseSunset);
        return fetchedSunriseSunset;
    }

    public void updateCity(long id, String cityName) {
        City city = cityRepository.findByCityId(id);
        List<SunriseSunset> sunriseSunsets = sunriseSunsetRepository.findAllByCity_CityId(id);
        city.setCityName(cityName);
        cityRepository.save(city);

        for (SunriseSunset sunriseSunset : sunriseSunsets) {
            sunriseSunset.setCityName(cityName);
            sunriseSunsetRepository.save(sunriseSunset);
        }
    }

    @Transactional
    public void deleteCityAndSunriseSunset(long cityId) {
        cityRepository.deleteByCityId(cityId);
    }
}
