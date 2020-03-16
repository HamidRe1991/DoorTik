package com.smr.estate.Model;

public class SpnSelectCity {
    private int id;
    private String cityName;

    public SpnSelectCity() {
    }

    public SpnSelectCity(int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
