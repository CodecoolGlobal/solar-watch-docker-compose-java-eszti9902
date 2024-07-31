package com.codecool.solarwatch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cityId;
    private String cityName;
    private String state;
    private String country;
    private float latitude;
    private float longitude;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SunriseSunset> sunriseSunsetList;

    public long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<SunriseSunset> getSunriseSunsetList() {
        return sunriseSunsetList;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setSunriseSunsetList(List<SunriseSunset> sunriseSunsetList) {
        this.sunriseSunsetList = sunriseSunsetList;
    }
}
