package com.example.farm.Modals;

public class EmployeeModel {

    String id, name,number,role,address,gender,nextofkin,contactnextofkim,age;

    public EmployeeModel(String id, String name, String number, String role, String address,
                         String gender ,String nextofkin,String contactnextofkim,String age) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.role = role;
        this.address = address;
        this.gender = gender;
        this.nextofkin = nextofkin;
        this.contactnextofkim = contactnextofkim;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNextofkin() {
        return nextofkin;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public String getContactnextofkim() {
        return contactnextofkim;
    }

    public void setContactnextofkim(String contactnextofkim) {
        this.contactnextofkim = contactnextofkim;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
