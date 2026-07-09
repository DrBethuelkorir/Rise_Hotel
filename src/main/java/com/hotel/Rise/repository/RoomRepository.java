package com.hotel.Rise.repository;

import com.hotel.Rise.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List <String> findDistinctRoomType();

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT bk.room.id from Booking bk )")
    List <Room> findAllAvailableRooms();

    @Query("SELECT r FROM Room r " +
            "WHERE r.roomType = :type " +
            "AND r.id NOT IN (" +
            "    SELECT bk.room.id FROM Booking bk " +
            "    WHERE bk.checkInDate < :checkOutDate " +
            "    AND bk.checkOutDate > :checkInDate" +
            ")")
    List<Room> findAvailableRoomsByDateAndRoomType(LocalDate checkInDate, LocalDate checkOutDate, String type);
}
