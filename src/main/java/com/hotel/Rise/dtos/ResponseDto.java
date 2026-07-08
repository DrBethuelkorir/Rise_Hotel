package com.hotel.Rise.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.Rise.models.Booking;
import lombok.Data;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private  String statusCode;
    private String message;
    private String token;
    private String role;
    private Date expirationTime;
    private String confirmationCode;
    private UserDto  user;
    private RoomDto room;
    private BookingDto booking;
    private List<UserDto> usersList;
    private List<RoomDto> roomList;
    private List<BookingDto> bookingList;
}
