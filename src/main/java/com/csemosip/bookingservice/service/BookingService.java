package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.BookingDTO;

import com.csemosip.bookingservice.exception.BookingNotFoundException;

import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.service.Impl.BookingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements BookingServiceImpl {


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBookedResourcesById(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
    }
}
