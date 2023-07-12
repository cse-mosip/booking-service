package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import com.csemosip.bookingservice.service.Impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class Bookings extends AbstractController {
    @Autowired
    BookingServiceImpl bookingService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllBookings() {
        List<Booking> bookings = bookingService.findAllBookings();
        return sendSuccessResponse(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findBookedResourcesById(@PathVariable Integer id) {
        Booking booking = bookingService.findBookedResourcesById(id);
        return sendSuccessResponse(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.createBooking(bookingDTO);
        return sendSuccessResponse(booking, HttpStatus.CREATED);
    }
}
