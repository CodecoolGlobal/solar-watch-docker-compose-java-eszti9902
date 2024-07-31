package com.codecool.solarwatch.model;

import jakarta.persistence.*;

@Entity
public class SunriseSunset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long SunId;
    private String cityName;
    private String date;
    private String sunrise;
    private String sunset;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", nullable = false)
    private City city;

    public String getCityName() {
        return cityName;
    }

    public String getDate() {
        return date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
