package com.hotel.Rise.contollers;

import com.hotel.Rise.Service.Room.IRoomService;
import com.hotel.Rise.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRooms(
            @RequestParam(value = "photo",required = false)MultipartFile photo,
            @RequestParam(value = "roomType",required = false)String roomType,
            @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
                @RequestParam(value = "roomDescription",required = false)String roomDescription

            ) {
        if ( roomType == null ||roomType.isBlank()
                || roomPrice == null  ) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Include values roomType,roomType,roomPrice");
        return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomService.addRoom(photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms() {
        Response response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }
    @GetMapping("/get-room-byId/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable("roomId") Long roomId) {
        Response response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAvailableRooms() {
        Response response = roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/available-rooms-bt-date-and-type")
    public ResponseEntity<Response> availableRoomsByDateAndType(
            @RequestParam(required = false)LocalDate checkInDate,
            @RequestParam(required = false)LocalDate checkOutDate,
            @RequestParam(required = false) String roomType


    ) {
        if ( roomType == null ||roomType.isBlank()
                || checkInDate == null || checkOutDate == null ) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Include values roomType,checkOutDate,checkInDate");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRooms(
            @PathVariable Long roomId,
            @RequestParam(value = "photo",required = false)MultipartFile photo,
            @RequestParam(value = "roomType",required = false)String roomType,
            @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomDescription",required = false)String roomDescription

    ){
        Response response = roomService.updateRoom(roomId, photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomID){
        Response response = roomService.deleteRoom(roomID);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
