package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.Impl.ResourceServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService implements ResourceServiceImpl {


    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Resource> findAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource createResource(ResourceDTO resourceDTO) {
        Resource resource = new Resource();
        resource.setName(resourceDTO.getName());
        resource.setCount(resourceDTO.getCount());

        return resourceRepository.save(resource);
    }

    @Override
    public Resource findResource(Long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
    }

    @Override
    public Resource updateResource(Long id, ResourceDTO resourceDTO) {
        Resource resource = resourceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        resource.setName(resourceDTO.getName());
        resource.setCount(resourceDTO.getCount());
        return resourceRepository.save(resource);
    }
}
