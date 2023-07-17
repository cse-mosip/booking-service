package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceServiceImpl {
    List<Resource> findAllResources();

    Resource createResource(ResourceDTO resourceDTO);

    Resource findResource(Long id);

    Resource updateResource(Long id, ResourceDTO resourceDTO);

    List<Map<String, Object>> getAvailabilityByResourceIdAndTimeslot(Long resourceId, String timeslot);
}
