package com.assignment.jitpay.jitpayassignment.dto;

import java.util.ArrayList;
import java.util.List;

public class UserLocationDto {
    private String userId;
    private List<LocationDto> locations = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }
}
