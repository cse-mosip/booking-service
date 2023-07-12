package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;

import java.util.List;

public interface BookingServiceImpl {
    List<Booking> findAllBookings();

    Booking createBooking(BookingDTO bookingDTO);

    Booking findBookedResourcesById(Integer id);
}
