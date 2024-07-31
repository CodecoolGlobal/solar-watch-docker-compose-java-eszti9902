package com.codecool.solarwatch.model.payload;

import lombok.Data;

@Data
public class CreateCityRequest {
    private String cityName;
    private String date;
}
