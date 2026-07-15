package com.hotel.Rise.Service.Room;

import com.hotel.Rise.Exeption.OurExeption;
import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.dtos.RoomDto;
import com.hotel.Rise.models.Room;
import com.hotel.Rise.repository.BookingRepository;
import com.hotel.Rise.repository.RoomRepository;
import com.hotel.Rise.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;


    @Override
    public Response addRoom(MultipartFile file, String roomType, BigDecimal roomPrice, String description) {

        Response response = new Response();


        try {
            Room room = new Room();
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setDescription(description);
            room.setRoomPhotoUrl(null);
            Room savedRoom = roomRepository.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Room has been added");
            response.setRoom(roomDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateRoom(long id, MultipartFile file, String roomType,
                               BigDecimal roomPrice, String description) {

        Response response = new Response();

        try {
            Room room = roomRepository.findById(id).orElseThrow(()->new OurExeption("Room not found"));
            if(room.getRoomPrice() != null)room.setRoomPrice(roomPrice);
            if(room.getDescription() != null)room.setDescription(description);
            if(room.getRoomPhotoUrl() != null)room.setRoomPhotoUrl(null);
            Room savedRoom = roomRepository.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Room has been updated");
            response.setRoom(roomDto);
        } catch (OurExeption e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(()->new OurExeption("Room not found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Room has been deleted");
        } catch (OurExeption e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<Room> rooms = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDto> RoomListDto = Utils.mapRoomListEntityToRoomListDTO(rooms);
            response.setStatusCode(200);
            response.setMessage("Room has been added");
            response.setRoomList(RoomListDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(()->new OurExeption("Room not found"));
            RoomDto roomDto = Utils.mapRoomEntityToRoomDTOPlusBooking(room);
            response.setStatusCode(200);
            response.setMessage("Room has been found");
            response.setRoom(roomDto);
        } catch (OurExeption e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> rooms =  roomRepository.findAllAvailableRooms();
            List<RoomDto> roomListDto = Utils.mapRoomListEntityToRoomListDTO(rooms);
            response.setStatusCode(200);
            response.setRoomList(roomListDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType
            (LocalDate checkInDate, LocalDate checkOutDate, String type) {
        Response response = new Response();
        try {
        List<Room> room = roomRepository.findAvailableRoomsByDateAndRoomType(checkInDate, checkOutDate, type);
        List<RoomDto> roomDtoList = Utils.mapRoomListEntityToRoomListDTO(room);
        response.setStatusCode(200);
        response.setMessage("Room has been found");
        response.setRoomList(roomDtoList);
    } catch (OurExeption e) {
        response.setStatusCode(400);
        response.setMessage(e.getMessage());
    }catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage(e.getMessage());
    }
        return response;
}

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }
}
