package com.csemosip.bookingservice.controller;

import com.csemosip.bookingservice.dto.BookingDTO;
import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingsController extends AbstractController {

    Logger log = LoggerFactory.getLogger(BookingsController.class);
    @Autowired
    BookingService bookingService;

    @Value("${mosip-auth-service-url}")
    String mosipAuthenticationServiceUrl;

    @Value("${allowed-time-window-before-booking-in-mins}")
    int allowedTimeBeforeABooking;

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

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER')")
    public ResponseEntity<Map<String, Object>> checkIn(
            @RequestBody Map<String, Object> requestBody
    ) {
        Long resourceId = ((Integer) requestBody.get("resourceId")).longValue();
        Object fingerprint = requestBody.get("fingerprint");

        // Authenticate fingerprint data with auth service
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> request = new HttpEntity<>(fingerprint);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                mosipAuthenticationServiceUrl,
                request,
                String.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return sendBadRequestResponse("Invalid user data.");
        }

        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String username = jsonNode.get("username").asText();

        // Check if the given user has a booking for the given resource id
        List<Booking> bookings = bookingService.findBookingsByUsernameAndResourceIdAndDate(
                username,
                resourceId,
                LocalDateTime.now()
        );

        LocalDateTime now = LocalDateTime.now();
        for (Booking booking : bookings) {
            String bookingStatus = booking.getStatus();
            if (booking.getStartTime().minusMinutes(allowedTimeBeforeABooking).isBefore(now) &&
                    booking.getEndTime().isAfter(now) &&
                    bookingStatus.equals("APPROVED")
            ) {
                bookingService.updateBookingStatus(booking, "IN-USE");
                HashMap<String, Object> responseObject = new HashMap<>();
                responseObject.put("username", username);
                responseObject.put("startTime", booking.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
                responseObject.put("endTime", booking.getEndTime().format(DateTimeFormatter.ISO_DATE_TIME));
                responseObject.put("count", booking.getCount());
                return sendSuccessResponse(responseObject, HttpStatus.OK);
            }
        }

        return sendBadRequestResponse("Booking not available for the given user at this time.");
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RESOURCE_MANAGER')")
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
