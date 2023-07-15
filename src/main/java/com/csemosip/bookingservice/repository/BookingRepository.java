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
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.resource.id = :resourceId AND DATE(b.bookedDate) = :bookedDate")
    List<Booking> findByResource_IdAndBookedDate(@Param("resourceId") Long resourceId, @Param("bookedDate") LocalDate bookedDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.resource.id = :resourceId AND b.startTime <= :endTime AND b.endTime >= :startTime")
    int getCountOfBookingsForResourceInTimeslot(
            @Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
