package com.project.bumawiki.domain.image.payload;

import java.util.List;

public class Response {
    private String message;
    private String imageLocation;
    private List<String> imageLocations;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public List<String> getImageLocations() {
        return imageLocations;
    }

    public void setImageLocations(List<String> imageLocations) {
        this.imageLocations = imageLocations;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}