package com.csemosip.bookingservice.service.impl;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.dto.ResourceAvailabilityDTO;
import com.csemosip.bookingservice.exception.BookingNotFoundException;
import com.csemosip.bookingservice.exception.ValidationErrorException;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.BookingService;
import com.csemosip.bookingservice.service.ResourceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Resource resource = resourceRepository.findById(bookingDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + bookingDTO.getResourceId()));

        // check if the booking count exceeds the max count of the resource
        if (bookingDTO.getCount() > resource.getCount())
            throw new ValidationErrorException("count cannot exceed the max count of the resource.");

        // check if the booking count exceeds the MIN available count during the timeslot
        List<ResourceAvailabilityDTO> availabilityList = resourceService.getAvailabilityByResourceIdAndTimeslot(
                resource.getId(),
                bookingDTO.getStartTime(),
                bookingDTO.getEndTime()
        );

        for (ResourceAvailabilityDTO availabilityDTO : availabilityList) {
            if (availabilityDTO.getCount() < bookingDTO.getCount())
                throw new ValidationErrorException("count cannot exceed the available count during the timeslot.");
        }

        Booking booking = new Booking();
        modelMapper.map(bookingDTO, booking);
        booking.setResource(resource);
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBookedResourcesById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));

        return booking;
    }

    @Override
    public List<Booking> findByResourceId(long resourceId) {
        return bookingRepository.findByResourceId(resourceId);
    }

    @Override
    public List<Booking> findByBookedDate(LocalDate bookedDate) {
        return bookingRepository.findByBookedDate(bookedDate);
    }

    @Override
    public List<Booking> findBookingsByResourceIdAndDate(long resourceId, LocalDate bookedDate) {
        return bookingRepository.findByResourceIdAndBookedDate(resourceId, bookedDate);
    }

    @Override
    public Booking updateBookingStatus(Booking booking, String status) {
        if(booking==null){
            throw new BookingNotFoundException("Booking Not Found");
        }
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

}
