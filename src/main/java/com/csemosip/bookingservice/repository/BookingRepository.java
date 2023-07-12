package com.csemosip.bookingservice.repository;

import com.csemosip.bookingservice.model.Booking;
import com.csemosip.bookingservice.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
