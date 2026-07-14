package com.hotel.Rise.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.Rise.models.Room;
import com.hotel.Rise.models.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto {
    private int id;
    private Date checkInDate;
    private Date checkOutDate;
    private Long numOfAdults;
    private Long numOfChildren;
    private String bookingConfirmationCode;
    private UserDto user;
    private RoomDto room;
}
