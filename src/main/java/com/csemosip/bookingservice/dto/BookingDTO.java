package com.csemosip.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {

    @JsonProperty("id")
    @Column(name="id")
    private long id;

    @JsonProperty("resource_id")
    @Column(name = "resource_id")
    private long resourceId;

    @JsonProperty("user_id")
    @Column(name="user_id")
    private long userId;

    @JsonProperty("booked_date")
    @Column(name = "booked_date")
    private LocalDateTime bookedDate;

    @JsonProperty("start_time")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    @Column(name = "end_time")
    private LocalDateTime endTime;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(LocalDateTime bookedDate) {
        this.bookedDate = bookedDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
