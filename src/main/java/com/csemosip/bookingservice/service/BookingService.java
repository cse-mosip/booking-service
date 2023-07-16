package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.BookingDTO;

import com.csemosip.bookingservice.exception.BookingNotFoundException;

import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.repository.ResourceRepository;
import com.csemosip.bookingservice.service.Impl.BookingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService implements BookingServiceImpl {


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Resource resource = resourceRepository.findById(bookingDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + bookingDTO.getResourceId()));

        Booking booking = new Booking();
        booking.setResource(resource);
        booking.setUserId(bookingDTO.getUserId());
        booking.setBookedDate(bookingDTO.getBookedDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setCount(bookingDTO.getCount());
        booking.setReason(bookingDTO.getReason());
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
}
