package com.csemosip.bookingservice.repository;

import com.csemosip.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByResourceIdAndBookedDate(Long resourceId, LocalDateTime bookedDate);

    List<Booking> findByResourceId(Long resourceId);

    List<Booking> findByBookedDate(LocalDateTime bookedDate);
}
