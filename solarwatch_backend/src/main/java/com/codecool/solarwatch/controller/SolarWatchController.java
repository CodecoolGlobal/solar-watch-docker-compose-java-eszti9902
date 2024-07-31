package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.WrongUrlException;
import com.codecool.solarwatch.model.CityReport;
import com.codecool.solarwatch.model.SolarReport;
import com.codecool.solarwatch.model.payload.CityRequest;
import com.codecool.solarwatch.model.payload.CreateCityRequest;
import com.codecool.solarwatch.service.OpenSolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class SolarWatchController {
    private final OpenSolarWatchService openSolarWatchService;

    @Autowired
    public SolarWatchController(OpenSolarWatchService openSolarWatchService) {
        this.openSolarWatchService = openSolarWatchService;
    }

    @GetMapping("/solar-watch")
    public List<SolarReport> getSolarReportsFromDatabase() {
        return openSolarWatchService.getAllSolarReport();
    }

    @GetMapping("/solarwatch")
    public SolarReport getSolarReport(@RequestParam(defaultValue = "Budapest") String city, @RequestParam String date) throws InvalidDateException, WrongUrlException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
        return openSolarWatchService.getDatabaseSolarReport(city, date);
    }

    @GetMapping("/admin/solarwatch/{cityId}")
    public CityReport getCityReportByCityId(@PathVariable long cityId) {
        return openSolarWatchService.getCityReportByCityId(cityId);
    }

    @PostMapping("/admin/solarwatch/create")
    public SolarReport getSolarReport(@RequestBody CreateCityRequest cityRequest) throws InvalidDateException, WrongUrlException {
        try {
            LocalDate.parse(cityRequest.getDate());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException();
        }
        return openSolarWatchService.getSolarReportFor(cityRequest.getCityName(), cityRequest.getDate());
    }

    @PutMapping("/admin/solarwatch/edit/{id}")
    public void updateCity(@PathVariable long id, @RequestBody CityRequest cityRequest) {
        openSolarWatchService.updateCity(id, cityRequest.getCityName());
    }


    @DeleteMapping("/admin/solarwatch/delete/{id}")
    public void deleteCity(@PathVariable long id) {
        openSolarWatchService.deleteCityAndSunriseSunset(id);
    }
}
