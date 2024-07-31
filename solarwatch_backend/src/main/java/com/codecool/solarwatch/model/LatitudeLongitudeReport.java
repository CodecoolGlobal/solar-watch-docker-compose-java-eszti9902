package com.codecool.solarwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LatitudeLongitudeReport(String name, float lat, float lon, String country, String state) {
}
