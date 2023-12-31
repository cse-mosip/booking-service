package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<Booking> findAllBookings();

    Booking createBooking(BookingDTO bookingDTO);

    Booking findBookedResourcesById(Long id);

    List<Booking> findByResourceId(long id);

    List<Booking> findByBookedDate(LocalDate bookedDate);

    List<Booking> findBookingsByResourceIdAndDate(long resourceId, LocalDate bookedDate);

    List<Booking> findBookingsByUsernameAndResourceIdAndDate(String username, long resourceId, LocalDateTime date);

    Booking updateBookingStatus(Booking booking, String status);
}
