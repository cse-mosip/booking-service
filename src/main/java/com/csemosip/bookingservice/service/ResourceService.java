package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.ResourceAvailabilityDTO;
import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;

import java.time.LocalDateTime;
import java.util.List;

public interface ResourceService {
    List<Resource> findAllResources();

    Resource createResource(ResourceDTO resourceDTO);

    Resource findResource(Long id);

    Resource updateResource(Long id, ResourceDTO resourceDTO);

    List<ResourceAvailabilityDTO> getAvailabilityByResourceIdAndTimeslot(
            Long resourceId,
            LocalDateTime start,
            LocalDateTime end
    );
}
