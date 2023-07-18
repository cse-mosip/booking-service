package com.csemosip.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {

    @JsonProperty("resource_id")
    private long resourceId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("booked_date")
    private LocalDateTime bookedDate;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("count")
    private int count;

    @JsonProperty("reason")
    private String reason;
}
