package com.example.customnavigationdrawer;

public class User {
    private String Name;
    private String Phone;
    private String Address;
    private String ID;
    private String Muitiem;
    private String Quet;

    public User() {
    }

    public User(String name, String phone, String address, String id, String muitiem, String quet) {

        Name = name;
        Phone = phone;
        Address = address;
        ID = id;
        Muitiem = muitiem;
        Quet = quet;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMuitiem() {
        return Muitiem;
    }

    public void setMuitiem(String muitiem) {
        Muitiem = muitiem;
    }

    public String getQuet() {
        return Quet;
    }

    public void setQuet(String quet) {
        Quet = quet;
    }
}
