package com.csemosip.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceDTO {
    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private int count;
}
