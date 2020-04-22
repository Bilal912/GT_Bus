
package com.example.bus_reservation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mybooking_model {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("trip_route_name")
    @Expose
    private String routeName;

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @SerializedName("id_no")
    @Expose
    private String idNo;
    @SerializedName("trip_id_no")
    @Expose
    private String tripIdNo;
    @SerializedName("tkt_passenger_id_no")
    @Expose
    private String tktPassengerIdNo;
    @SerializedName("trip_route_id")
    @Expose
    private String tripRouteId;
    @SerializedName("pickup_trip_location")
    @Expose
    private String pickupTripLocation;
    @SerializedName("drop_trip_location")
    @Expose
    private String dropTripLocation;
    @SerializedName("request_facilities")
    @Expose
    private String requestFacilities;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("total_seat")
    @Expose
    private String totalSeat;
    @SerializedName("seat_numbers")
    @Expose
    private String seatNumbers;
    @SerializedName("offer_code")
    @Expose
    private Object offerCode;
    @SerializedName("tkt_refund_id")
    @Expose
    private Object tktRefundId;
    @SerializedName("agent_id")
    @Expose
    private Object agentId;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;

    private String time_added;

    public String getTime_added() {
        return time_added;
    }

    public void setTime_added(String time_added) {
        this.time_added = time_added;
    }

    /**
     * No args constructor for use in serialization
     *
     */

    public mybooking_model() {
    }

    public mybooking_model(String id, String routeName, String idNo, String tripIdNo, String tktPassengerIdNo, String tripRouteId, String pickupTripLocation, String dropTripLocation, String requestFacilities, String price, Object discount, String totalSeat, String seatNumbers, Object offerCode, Object tktRefundId, Object agentId, String bookingDate, String date, String bookingType, String paymentStatus, String transactionId, String time_added) {
        this.id = id;
        this.routeName = routeName;
        this.idNo = idNo;
        this.tripIdNo = tripIdNo;
        this.tktPassengerIdNo = tktPassengerIdNo;
        this.tripRouteId = tripRouteId;
        this.pickupTripLocation = pickupTripLocation;
        this.dropTripLocation = dropTripLocation;
        this.requestFacilities = requestFacilities;
        this.price = price;
        this.discount = discount;
        this.totalSeat = totalSeat;
        this.seatNumbers = seatNumbers;
        this.offerCode = offerCode;
        this.tktRefundId = tktRefundId;
        this.agentId = agentId;
        this.bookingDate = bookingDate;
        this.date = date;
        this.bookingType = bookingType;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.time_added = time_added;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTripIdNo() {
        return tripIdNo;
    }

    public void setTripIdNo(String tripIdNo) {
        this.tripIdNo = tripIdNo;
    }

    public String getTktPassengerIdNo() {
        return tktPassengerIdNo;
    }

    public void setTktPassengerIdNo(String tktPassengerIdNo) {
        this.tktPassengerIdNo = tktPassengerIdNo;
    }

    public String getTripRouteId() {
        return tripRouteId;
    }

    public void setTripRouteId(String tripRouteId) {
        this.tripRouteId = tripRouteId;
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

    public String getRequestFacilities() {
        return requestFacilities;
    }

    public void setRequestFacilities(String requestFacilities) {
        this.requestFacilities = requestFacilities;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(String totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Object getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(Object offerCode) {
        this.offerCode = offerCode;
    }

    public Object getTktRefundId() {
        return tktRefundId;
    }

    public void setTktRefundId(Object tktRefundId) {
        this.tktRefundId = tktRefundId;
    }

    public Object getAgentId() {
        return agentId;
    }

    public void setAgentId(Object agentId) {
        this.agentId = agentId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
