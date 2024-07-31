//package com.codecool.solarwatch.service;
//
//import com.codecool.solarwatch.controller.SolarWatchController;
//import com.codecool.solarwatch.exception.DataNotInDatabaseException;
//import com.codecool.solarwatch.exception.InvalidDateException;
//import com.codecool.solarwatch.repository.CityRepository;
//import com.codecool.solarwatch.repository.SunriseSunsetRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//public class OpenSolarWatchServiceTest {
//
//    private RestTemplate restTemplate;
//    private OpenSolarWatchService openSolarWatchService;
//    private SolarWatchController solarWatchController;
//
//    @Mock
//    private CityRepository cityRepository;
//
//    @Mock
//    private SunriseSunsetRepository sunriseSunsetRepository;
//
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//        this.restTemplate = new RestTemplate();
//        this.openSolarWatchService = new OpenSolarWatchService(restTemplate, cityRepository, sunriseSunsetRepository);
//        this.solarWatchController = new SolarWatchController(openSolarWatchService);
//    }
//
//
//    @Test
//    void solarWatchControllerWrongDate() {
//        assertThrows(InvalidDateException.class, () -> solarWatchController.getSolarReport("London", "2024-16-54"));
//    }
//
//    @Test
//    void solarWatchControllerInvalidCity() {
//        assertThrows(DataNotInDatabaseException.class, () -> solarWatchController.getSolarReport("bbb", "2024-07-29"));
//    }
//
//}