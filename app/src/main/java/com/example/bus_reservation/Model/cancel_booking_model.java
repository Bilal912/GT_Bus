package com.example.bus_reservation.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cancel_booking_model {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payment_image")
    @Expose
    private String image;
    private String refund_fee;

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("tkt_booking_id_no")
    @Expose
    private String tktBookingIdNo;
    @SerializedName("tkt_passenger_id_no")
    @Expose
    private String tktPassengerIdNo;
    @SerializedName("cancelation_fees")
    @Expose
    private String cancelationFees;
    @SerializedName("causes")
    @Expose
    private String causes;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("refund_by_id")
    @Expose
    private String refundById;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("payment_image")
    @Expose
    private String paymentImage;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTktBookingIdNo() {
        return tktBookingIdNo;
    }

    public void setTktBookingIdNo(String tktBookingIdNo) {
        this.tktBookingIdNo = tktBookingIdNo;
    }

    public String getTktPassengerIdNo() {
        return tktPassengerIdNo;
    }

    public void setTktPassengerIdNo(String tktPassengerIdNo) {
        this.tktPassengerIdNo = tktPassengerIdNo;
    }

    public String getCancelationFees() {
        return cancelationFees;
    }

    public void setCancelationFees(String cancelationFees) {
        this.cancelationFees = cancelationFees;
    }

    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRefundById() {
        return refundById;
    }

    public void setRefundById(String refundById) {
        this.refundById = refundById;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentImage() {
        return paymentImage;
    }

    public void setPaymentImage(String paymentImage) {
        this.paymentImage = paymentImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
