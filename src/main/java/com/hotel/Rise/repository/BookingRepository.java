package com.hotel.Rise.repository;

import com.hotel.Rise.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
git add . ; git commit -m "Added all Hotel entities and DTOs" ; git push