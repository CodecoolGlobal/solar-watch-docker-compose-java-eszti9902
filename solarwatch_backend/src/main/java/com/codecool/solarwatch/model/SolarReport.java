package com.codecool.solarwatch.model;

public record SolarReport(long cityId, String city, float lat, float lon, String date, String sunrise, String sunset) {
}
