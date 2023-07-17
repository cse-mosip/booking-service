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

    @Query("SELECT b FROM Booking b WHERE b.resource.id = :resourceId AND DATE(b.bookedDate) = :bookedDate")
    List<Booking> findByResourceIdAndBookedDate(@Param("resourceId") Long resourceId, @Param("bookedDate") LocalDate bookedDate);

    List<Booking> findByResourceId(Long resourceId);

    @Query("SELECT b FROM Booking b WHERE DATE(b.bookedDate) = :bookedDate")
    List<Booking> findByBookedDate(@Param("bookedDate") LocalDate bookedDate);
}
