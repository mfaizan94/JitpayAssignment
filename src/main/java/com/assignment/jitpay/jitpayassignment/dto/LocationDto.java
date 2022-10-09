package com.assignment.jitpay.jitpayassignment.dto;

public class LocationDto {

    private String createdOn;
    private Location location = new Location();

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
