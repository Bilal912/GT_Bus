package com.example.bus_reservation.Model;


public class seatdetail_model {
    String Name;

    public seatdetail_model(String name, String number) {
        Name = name;
        Number = number;
    }

    String Number;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}

