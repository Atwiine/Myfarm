package com.example.farm.Modals;

public class MatookeModel {

    String id, numberBanches,date;

    public MatookeModel(String id, String numberBanches, String date) {
        this.id = id;
        this.numberBanches = numberBanches;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberBanches() {
        return numberBanches;
    }

    public void setNumberBanches(String numberBanches) {
        this.numberBanches = numberBanches;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
