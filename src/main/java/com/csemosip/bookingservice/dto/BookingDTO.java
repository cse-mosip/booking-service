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

    @JsonProperty("resource_id")
    @Column(name = "resource_id")
    private long resourceId;

    @JsonProperty("user_id")
    @Column(name="user_id")
    private String userId;

    @JsonProperty("booked_date")
    @Column(name = "booked_date")
    private LocalDateTime bookedDate;

    @JsonProperty("start_time")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    @Column(name = "end_time")
    private LocalDateTime endTime;
}
