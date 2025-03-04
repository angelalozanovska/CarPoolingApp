package com.example.carpooling.Models;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable{
    Integer id;
    String name;
    String email;
    String password;
    Integer rating;
    Boolean isDriver;
    Integer vehicleId;
    Vehicle vehicle;
    List<Trip> trips;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDriver() {
        return isDriver;
    }

    public void setDriver(Boolean driver) {
        isDriver = driver;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public User(){
    }

    public User(int id, String name, String email, String password, int rating, boolean isDriver, int vehicleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rating = rating;
        this.isDriver = isDriver;
        this.vehicleId = vehicleId;
    }
}
