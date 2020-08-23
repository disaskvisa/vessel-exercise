package com.disa.vessel.entities;

import java.util.Date;
import java.util.Objects;

public class Position {

    // Note: Description specified _date_, but presumably Instant makes more sense here.
    private final Date date;
    private final double latitude;
    private final double longitude;
    private final double speed;
    private Date receivedDate;

    public Position(Date date, double latitude, double longitude, double speed) {
        this.date = Objects.requireNonNull(date, "Position date required");

        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude range is [-90,90]");
        }
        this.latitude = latitude;

        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude range is [-180,180]");
        }
        this.longitude = longitude;

        // As vessels can go in reverse, no range check applied here
        this.speed = speed;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public Date getDate() {
        return date;
    }
}
