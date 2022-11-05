package com.example.farm.Modals;

public class MilkResultsModel {

    String id, total, home,diary,comment,date,time;

    public MilkResultsModel(String id, String total, String home, String diary, String comment, String date,String time) {
        this.id = id;
        this.total = total;
        this.home = home;
        this.diary = diary;
        this.comment = comment;
        this.date = date;
        this.time = time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
