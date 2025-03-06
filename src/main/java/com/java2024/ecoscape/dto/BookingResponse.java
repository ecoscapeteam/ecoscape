package com.java2024.ecoscape.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.java2024.ecoscape.models.Status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long listingId;
    private String firstName;
    private String lastName;
    private String usersContactPhoneNumber;
    private String usersContactEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private int guests;
    private BigDecimal pricePerNight;
    private BigDecimal websiteFee;
    private BigDecimal cleaningFee;
    private BigDecimal totalPrice;
    private String pricePerNightWithCurrency;
    private String websiteFeeWithCurrency;
    private String cleaningFeeWithCurrency;
    private String totalPriceWithCurrency;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsersContactPhoneNumber() {
        return usersContactPhoneNumber;
    }

    public void setUsersContactPhoneNumber(String usersContactPhoneNumber) {
        this.usersContactPhoneNumber = usersContactPhoneNumber;
    }

    public String getUsersContactEmail() {
        return usersContactEmail;
    }

    public void setUsersContactEmail(String usersContactEmail) {
        this.usersContactEmail = usersContactEmail;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public BigDecimal getWebsiteFee() {
        return websiteFee;
    }

    public void setWebsiteFee(BigDecimal websiteFee) {
        this.websiteFee = websiteFee;
    }

    public BigDecimal getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(BigDecimal cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getters مع عملة (لا يؤثر على القيم الأصلية)
    @JsonGetter("pricePerNightWithCurrency")
    public String getPricePerNightWithCurrency() {
        return formatWithCurrency(pricePerNight);
    }

    @JsonGetter("cleaningFeeWithCurrency")
    public String getCleaningFeeWithCurrency() {
        return formatWithCurrency(cleaningFee);
    }

    @JsonGetter("websiteFeeWithCurrency")
    public String getWebsiteFeeWithCurrency() {
        return formatWithCurrency(websiteFee);
    }

    @JsonGetter("totalPriceWithCurrency")
    public String getTotalPriceWithCurrency() {
        return formatWithCurrency(totalPrice);
    }

    // دالة مساعدة لإضافة العملة
    private String formatWithCurrency(BigDecimal value) {
        if (value == null) return "0.00 kr";
        return value.setScale(2, RoundingMode.HALF_UP) + " kr";
    }

    public void setPricePerNightWithCurrency(String pricePerNightWithCurrency) {
        this.pricePerNightWithCurrency = pricePerNightWithCurrency;
    }

    public void setCleaningFeeWithCurrency(String cleaningFeeWithCurrency) {
        this.cleaningFeeWithCurrency = cleaningFeeWithCurrency;
    }

    public void setTotalPriceWithCurrency(String totalPriceWithCurrency) {
        this.totalPriceWithCurrency = totalPriceWithCurrency;
    }

    public void setWebsiteFeeWithCurrency(String websiteFeeWithCurrency) {
        this.websiteFeeWithCurrency = websiteFeeWithCurrency;
    }
}
