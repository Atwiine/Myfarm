package com.example.farm.Modals;

public class AnimalResultsModel {

    String tagnumber, gender, id,date,weight,checker,parent_tagnumber;

    public AnimalResultsModel(String tagnumber, String gender, String id, String date, String weight, String checker,String parent_tagnumber) {
        this.tagnumber = tagnumber;
        this.gender = gender;
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.checker = checker;
        this.parent_tagnumber = parent_tagnumber;
    }

    public String getTagnumber() {
        return tagnumber;
    }

    public void setTagnumber(String tagnumber) {
        this.tagnumber = tagnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getParent_tagnumber() {
        return parent_tagnumber;
    }

    public void setParent_tagnumber(String parent_tagnumber) {
        this.parent_tagnumber = parent_tagnumber;
    }
}
