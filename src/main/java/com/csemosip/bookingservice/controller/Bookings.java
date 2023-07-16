package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.service.Impl.BookingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class Bookings extends AbstractController {

    Logger log = LoggerFactory.getLogger(Bookings.class);
    @Autowired
    BookingServiceImpl bookingService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findBookings(
            @RequestParam(name = "resource_id", required = false) Long resourceId,
            @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime bookedDate
    ) {
        List<Booking> bookings;

        if (resourceId != null && bookedDate != null) {
            bookings = bookingService.findBookingsByResourceIdAndDate(resourceId, bookedDate);
        } else if (resourceId != null) {
            bookings = bookingService.findByResourceId(resourceId);
        } else if (bookedDate != null) {
            bookings = bookingService.findByBookedDate(bookedDate);
        } else {
            // If either resourceId or bookedDate is not provided, fetch all bookings
            bookings = bookingService.findAllBookings();
        }

        return sendSuccessResponse(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findBookedResourcesById(@PathVariable Long id) {
        Booking booking = bookingService.findBookedResourcesById(id);
        return sendSuccessResponse(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.createBooking(bookingDTO);
        return sendSuccessResponse(booking, HttpStatus.CREATED);
    }
}