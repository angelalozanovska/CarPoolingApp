package com.example.carpooling.Models;

public class Attendee {
    Integer userId;
    User user;
    Integer tripId;
    Trip trip;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Attendee() {
    }

    public Attendee(Integer userId, User user, Integer tripId, Trip trip) {
        this.userId = userId;
        this.user = user;
        this.tripId = tripId;
        this.trip = trip;
    }
}
