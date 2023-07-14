package com.csemosip.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {

    @Column(name = "resource_id")
    private long resourceId;

    @Column(name="user_id")
    private long userId;

    @Column(name = "booked_date")
    private LocalDateTime bookedDate;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
