package com.csemosip.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceAvailabilityDTO {
    @JsonProperty("start")
    private LocalDateTime startTime;
    @JsonProperty("end")
    private LocalDateTime endTime;
    @JsonProperty("count")
    private int count;
}
