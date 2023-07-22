package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.ResourceAvailabilityDTO;
import com.csemosip.bookingservice.dto.ResourceDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.ResourceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ResourceServiceImpl implements ResourceService {


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
        Resource resource = new Resource();
        modelMapper.map(resourceDTO, resource);

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

    @Override
    public List<ResourceAvailabilityDTO> getAvailabilityByResourceIdAndTimeslot(
            Long resourceId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        // Perform the logic to get the availability count
        List<Booking> overlappingBookings = bookingRepository.getBookingsForResourceInTimeslot(
                resourceId,
                startTime,
                endTime
        );

        List<ResourceAvailabilityDTO>  availabilityList = new ArrayList<>();

        // Initialize availabilityList assuming max quantity is available for the timeslot
        ResourceAvailabilityDTO initialAvailability = new ResourceAvailabilityDTO(
                startTime,
                endTime,
                resource.getCount()
        );
        availabilityList.add(initialAvailability);

        for (Booking booking : overlappingBookings) {
            List<ResourceAvailabilityDTO> updatedAvailabilityList = new ArrayList<>();

            for (ResourceAvailabilityDTO availability : availabilityList) {
                // case 1 : booking covers the whole timeslot
                if (
                        (
                                (booking.getStartTime().isBefore(availability.getStartTime()) ||
                                booking.getStartTime().isEqual(availability.getStartTime()))
                                        &&
                                ((booking.getEndTime().isAfter(availability.getEndTime()) ||
                                booking.getEndTime().isEqual(availability.getEndTime())))

                        )
                ) {
                    ResourceAvailabilityDTO newAvailability = new ResourceAvailabilityDTO(
                            availability.getStartTime(),
                            availability.getEndTime(),
                            availability.getCount() - booking.getCount());
                    updatedAvailabilityList.add(newAvailability);
                }

                // case 2: booking has both start time & end time within timeslot
                else if (
                        booking.getStartTime().isAfter(availability.getStartTime()) &&
                        booking.getStartTime().isBefore(availability.getEndTime()) &&
                        booking.getEndTime().isAfter(availability.getStartTime()) &&
                        booking.getEndTime().isBefore(availability.getEndTime())
                ) {
                    ResourceAvailabilityDTO beforeBooking = new ResourceAvailabilityDTO(
                            availability.getStartTime(),
                            booking.getStartTime(),
                            availability.getCount()
                    );
                    ResourceAvailabilityDTO duringBooking = new ResourceAvailabilityDTO(
                            booking.getStartTime(),
                            booking.getEndTime(),
                            availability.getCount() - booking.getCount()
                    );
                    ResourceAvailabilityDTO afterBooking = new ResourceAvailabilityDTO(
                            booking.getEndTime(),
                            availability.getEndTime(),
                            availability.getCount()
                    );

                    updatedAvailabilityList.add(beforeBooking);
                    updatedAvailabilityList.add(duringBooking);
                    updatedAvailabilityList.add(afterBooking);
                }


                // case 3: booking has only the start time within the time slot
                else if (
                        booking.getStartTime().isAfter(availability.getStartTime()) &&
                        booking.getStartTime().isBefore(availability.getEndTime()) &&
                        booking.getEndTime().isAfter(availability.getEndTime())
                    ) {
                    ResourceAvailabilityDTO beforeBooking = new ResourceAvailabilityDTO(
                            availability.getStartTime(),
                            booking.getStartTime(),
                            availability.getCount()
                    );
                    ResourceAvailabilityDTO duringBooking = new ResourceAvailabilityDTO(
                            booking.getStartTime(),
                            availability.getEndTime(),
                            availability.getCount() - booking.getCount()
                    );

                    updatedAvailabilityList.add(beforeBooking);
                    updatedAvailabilityList.add(duringBooking);
                }

                // case 4: booking has only the end time within the time slot
                else if (
                        booking.getEndTime().isAfter(availability.getStartTime()) &&
                        booking.getEndTime().isBefore(availability.getEndTime()) &&
                        booking.getStartTime().isBefore(availability.getStartTime())
                ) {
                    ResourceAvailabilityDTO duringBooking = new ResourceAvailabilityDTO(
                            availability.getStartTime(),
                            booking.getEndTime(),
                            availability.getCount() - booking.getCount()
                    );
                    ResourceAvailabilityDTO afterBooking = new ResourceAvailabilityDTO(
                            booking.getEndTime(),
                            availability.getEndTime(),
                            availability.getCount()
                    );

                    updatedAvailabilityList.add(duringBooking);
                    updatedAvailabilityList.add(afterBooking);
                }
                else {
                    updatedAvailabilityList.add(availability);
                }
            }
            availabilityList = updatedAvailabilityList;
        }
        return availabilityList;
    }
}
