package com.codecool.solarwatch.service;

import com.codecool.solarwatch.controller.SolarWatchController;
import com.codecool.solarwatch.exception.DataNotInDatabaseException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.CityReport;
import com.codecool.solarwatch.model.SolarReport;
import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

public class OpenSolarWatchServiceTest {

    private RestTemplate restTemplate;
    private OpenSolarWatchService openSolarWatchService;
    private SolarWatchController solarWatchController;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;



    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.restTemplate = new RestTemplate();
        this.openSolarWatchService = new OpenSolarWatchService(restTemplate, cityRepository, sunriseSunsetRepository);
        this.solarWatchController = new SolarWatchController(openSolarWatchService);
    }

    @Test
    public void testGetCityReportByCityId() throws DataNotInDatabaseException {
        City city = new City();
        city.setCityName("London");
        city.setState("London");
        city.setCountry("Great Britain");
        city.setLatitude(51.2f);
        city.setLongitude(-87.0f);

        Mockito.when(cityRepository.findByCityId(anyLong())).thenReturn(city);
        CityReport cityReport = openSolarWatchService.getCityReportByCityId(city.getCityId());
        assertEquals("London", cityReport.cityName());
    }

    @Test
    public void testGetAllSolarReports() throws DataNotInDatabaseException {
        City city = new City();
        city.setCityName("London");
        city.setState("London");
        city.setCountry("Great Britain");
        city.setLatitude(51.2f);
        city.setLongitude(-87.0f);
        List<City> cities = List.of(city);

        SunriseSunset sunriseSunset = new SunriseSunset();
        sunriseSunset.setCityName("London");
        sunriseSunset.setSunrise("7:00 AM");
        sunriseSunset.setSunset("20:00 PM");
        sunriseSunset.setDate("2024-08-20");

        List<SunriseSunset> sunriseSunsetList = List.of(sunriseSunset);

        Mockito.when(cityRepository.findAll()).thenReturn(cities);
        Mockito.when(sunriseSunsetRepository.findAll()).thenReturn(sunriseSunsetList);
        List<SolarReport> expectedReports = new ArrayList<>();
        expectedReports.add(new SolarReport(0, "London", 51.2f, -87.0f, "2024-08-20", "7:00 AM", "20:00 PM"));
        List<SolarReport> actual = openSolarWatchService.getAllSolarReport();

        assertEquals(expectedReports, actual);
    }

    @Test
    public void testDeleteDbSolarReport() throws DataNotInDatabaseException {
        openSolarWatchService.deleteCityAndSunriseSunset(1);
        Mockito.verify(cityRepository).deleteByCityId(anyLong());
    }

    @Test
    public void testUpdateCityAndSunriseSunset() throws DataNotInDatabaseException {
        City city = new City();
        city.setCityName("London");
        city.setState("London");
        city.setCountry("Great Britain");
        city.setLatitude(51.2f);
        city.setLongitude(-87.0f);

        SunriseSunset sunriseSunset = new SunriseSunset();
        sunriseSunset.setCityName("London");
        sunriseSunset.setSunrise("7:00 AM");
        sunriseSunset.setSunset("20:00 PM");
        sunriseSunset.setDate("2024-08-20");

        List<SunriseSunset> sunriseSunsetList = List.of(sunriseSunset);

        Mockito.when(sunriseSunsetRepository.findAllByCity_CityId(anyLong())).thenReturn(sunriseSunsetList);
        Mockito.when(cityRepository.findByCityId(anyLong())).thenReturn(city);

        openSolarWatchService.updateCity(1, "City of the BigBen");
        sunriseSunset.setCityName("City of the BigBen");

        Mockito.verify(cityRepository).save(city);
        Mockito.verify(sunriseSunsetRepository).save(sunriseSunset);
    }

    @Test
    void solarWatchControllerWrongDate() {
        assertThrows(InvalidDateException.class, () -> solarWatchController.getSolarReport("London", "2024-16-54"));
    }

    @Test
    void solarWatchControllerInvalidCity() {
        assertThrows(DataNotInDatabaseException.class, () -> solarWatchController.getSolarReport("bbb", "2024-07-29"));
    }

}