package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.BookingDTO;

import com.csemosip.bookingservice.exception.BookingNotFoundException;

import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.repository.BookingRepository;
import com.csemosip.bookingservice.service.Impl.BookingServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public List<Booking> findBookingsByResourceIdAndDate(long resourceId, LocalDate bookedDate) {
        return bookingRepository.findByResource_IdAndBookedDate(resourceId, bookedDate);
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
