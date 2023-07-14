package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingServiceImpl {
    List<Booking> findAllBookings();

    Booking createBooking(BookingDTO bookingDTO);

    Booking findBookedResourcesById(Integer id);

    List<Booking> findBookingsByResourceIdAndDate(long resourceId, LocalDate bookedDate);

    Booking updateBooking(Booking booking);

}
