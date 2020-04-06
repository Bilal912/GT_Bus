
package com.example.bus_reservation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class booking_model {

    public booking_model() {
        this.tripIdNo = tripIdNo;
        this.fleetRegistrationId = fleetRegistrationId;
        this.tripRouteId = tripRouteId;
        this.tripRouteName = tripRouteName;
        this.pickupTripLocation = pickupTripLocation;
        this.dropTripLocation = dropTripLocation;
        this.fleetTypeId = fleetTypeId;
        this.fleetRegistrationNo = fleetRegistrationNo;
        this.fleetSeats = fleetSeats;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.bookingDate = bookingDate;
    }

    @SerializedName("trip_id_no")
    @Expose
    private String tripIdNo;
    @SerializedName("fleet_registration_id")
    @Expose
    private String fleetRegistrationId;
    @SerializedName("trip_route_id")
    @Expose
    private String tripRouteId;
    @SerializedName("trip_route_name")
    @Expose
    private String tripRouteName;
    @SerializedName("pickup_trip_location")
    @Expose
    private String pickupTripLocation;
    @SerializedName("drop_trip_location")
    @Expose
    private String dropTripLocation;
    @SerializedName("fleet_type_id")
    @Expose
    private String fleetTypeId;
    @SerializedName("fleet_registration_no")
    @Expose
    private String fleetRegistrationNo;
    @SerializedName("fleet_seats")
    @Expose
    private String fleetSeats;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;

    public String getTripIdNo() {
        return tripIdNo;
    }

    public void setTripIdNo(String tripIdNo) {
        this.tripIdNo = tripIdNo;
    }

    public String getFleetRegistrationId() {
        return fleetRegistrationId;
    }

    public void setFleetRegistrationId(String fleetRegistrationId) {
        this.fleetRegistrationId = fleetRegistrationId;
    }

    public String getTripRouteId() {
        return tripRouteId;
    }

    public void setTripRouteId(String tripRouteId) {
        this.tripRouteId = tripRouteId;
    }

    public String getTripRouteName() {
        return tripRouteName;
    }

    public void setTripRouteName(String tripRouteName) {
        this.tripRouteName = tripRouteName;
    }

    public String getPickupTripLocation() {
        return pickupTripLocation;
    }

    public void setPickupTripLocation(String pickupTripLocation) {
        this.pickupTripLocation = pickupTripLocation;
    }

    public String getDropTripLocation() {
        return dropTripLocation;
    }

    public void setDropTripLocation(String dropTripLocation) {
        this.dropTripLocation = dropTripLocation;
    }

    public String getFleetTypeId() {
        return fleetTypeId;
    }

    public void setFleetTypeId(String fleetTypeId) {
        this.fleetTypeId = fleetTypeId;
    }

    public String getFleetRegistrationNo() {
        return fleetRegistrationNo;
    }

    public void setFleetRegistrationNo(String fleetRegistrationNo) {
        this.fleetRegistrationNo = fleetRegistrationNo;
    }

    public String getFleetSeats(String fleet_seats) {
        return fleetSeats;
    }

    public void setFleetSeats(String fleetSeats) {
        this.fleetSeats = fleetSeats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getFleetSeats() {
        return fleetSeats;
    }
}
