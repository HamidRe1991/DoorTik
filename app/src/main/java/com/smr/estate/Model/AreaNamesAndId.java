package com.smr.estate.Model;

public class AreaNamesAndId {
    private String name;
    private  String namesId;

    public AreaNamesAndId(String name, String namesId) {
        this.name = name;
        this.namesId = namesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamesId() {
        return namesId;
    }

    public void setNamesId(String namesId) {
        this.namesId = namesId;
    }
}
