package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingsController extends AbstractController {

    Logger log = LoggerFactory.getLogger(BookingsController.class);
    @Autowired
    BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
    public ResponseEntity<Map<String, Object>> findBookings(
            @RequestParam(name = "resource_id", required = false) Long resourceId,
            @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime bookedDateTime
    ) {
        List<Booking> bookings;

        if (resourceId != null && bookedDateTime != null) {
            LocalDate bookedDate = bookedDateTime.toLocalDate();
            bookings = bookingService.findBookingsByResourceIdAndDate(resourceId, bookedDate);
        } else if (resourceId != null) {
            bookings = bookingService.findByResourceId(resourceId);
        } else if (bookedDateTime != null) {
            LocalDate bookedDate = bookedDateTime.toLocalDate();
            bookings = bookingService.findByBookedDate(bookedDate);
        } else {
            // If either resourceId or bookedDate is not provided, fetch all bookings
            bookings = bookingService.findAllBookings();
        }

        return sendSuccessResponse(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
    public ResponseEntity<Map<String, Object>> findBookedResourcesById(@PathVariable Long id) {
        Booking booking = bookingService.findBookedResourcesById(id);
        return sendSuccessResponse(booking, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER', 'RESOURCE_USER')")
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateBookingStatus(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> statusMap
    ) {
        String status = statusMap.get("status");
        Booking booking = bookingService.findBookedResourcesById(id);
        Booking updatedBooking = bookingService.updateBookingStatus(booking, status);
        return sendSuccessResponse(updatedBooking, HttpStatus.OK);
    }

}
