package com.hotel.Rise.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.Rise.models.Booking;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Long phoneNumber;
    private String role;
    private List<Booking> bookings = new ArrayList<>();
}
