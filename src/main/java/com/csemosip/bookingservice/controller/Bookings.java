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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class Bookings extends AbstractController {

    Logger log = LoggerFactory.getLogger(Bookings.class);
    @Autowired
    BookingServiceImpl bookingService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllBookings() {
        log.info("get bookings");
        List<Booking> bookings = bookingService.findAllBookings();
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

    @GetMapping("/{resourceId}/{bookedDate}")
    public ResponseEntity<List<Booking>> findBookingsByResourceIdAndDate(
            @PathVariable("resourceId") Long resourceId,
            @PathVariable("bookedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookedDate
    ) {
        List<Booking> bookings = bookingService.findBookingsByResourceIdAndDate(resourceId, bookedDate);
        return ResponseEntity.ok(bookings);
    }
}
