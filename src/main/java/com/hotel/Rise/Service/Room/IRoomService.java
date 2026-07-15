package com.hotel.Rise.Service.Room;

import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.models.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    Response addRoom(MultipartFile file, String roomType, BigDecimal roomPrice,String description);
    Response updateRoom(long id,MultipartFile file, String roomType, BigDecimal roomPrice,String description);
    Response deleteRoom(Long roomId);
    Response getAllRooms();
    Response getRoomById(Long roomId);
    Response getAllAvailableRooms();
    Response getAvailableRoomsByDateAndType(LocalDate checkInDate,LocalDate checkOutDate,String type);
    List<String> getAllRoomTypes();
}
