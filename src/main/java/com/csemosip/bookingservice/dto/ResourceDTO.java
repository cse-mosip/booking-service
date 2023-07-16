package com.csemosip.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("count")
    private int count;
}
