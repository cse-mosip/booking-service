package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingServiceImpl {
    List<Booking> findAllBookings();

    Booking createBooking(BookingDTO bookingDTO);

    Booking findBookedResourcesById(Long id);

    List<Booking> findByResourceId(long id);

    List<Booking> findByBookedDate(LocalDateTime bookedDate);

    List<Booking> findBookingsByResourceIdAndDate(long resourceId, LocalDateTime bookedDate);
}
