package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.Impl.ResourceServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ResourceService implements ResourceServiceImpl {


    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Resource> findAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource createResource(ResourceDTO resourceDTO) {
        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        return resourceRepository.save(resource);
    }

    @Override
    public Resource findResource(Long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
    }

    @Override
    public List<Map<String, Object>> getAvailabilityByResourceIdAndTimeslot(Long resourceId, String timeslot) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        // Parse the timeslot string and extract the start and end times
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            String[] times = timeslot.split("-");
            startTime = LocalDateTime.parse(times[0], DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            endTime = LocalDateTime.parse(times[1], DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid timeslot format");
        }

        // Perform the logic to get the availability count
        int bookedCount = bookingRepository.getCountOfBookingsForResourceInTimeslot(resourceId, startTime, endTime);
        int availableCount = resource.getCount() - bookedCount;

        // Prepare the response
        Map<String, Object> availability = new HashMap<>();
        availability.put("start", startTime.toString());
        availability.put("end", endTime.toString());
        availability.put("available", availableCount);

        return Collections.singletonList(availability);
    }


}
