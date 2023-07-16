package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.model.Resource;

import java.util.List;

public interface ResourceServiceImpl {
    List<Resource> findAllResources();

    Resource createResource(ResourceDTO resourceDTO);

    Resource findResource(Long id);
}
