package com.hotel.Rise.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private  int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;
    private String confirmationCode;
    private UserDto  user;
    private RoomDto room;
    private BookingDto booking;
    private List<UserDto> usersList;
    private List<RoomDto> roomList;
    private List<BookingDto> bookingList;
}
