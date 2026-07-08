package com.hotel.Rise.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.Rise.models.Booking;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto {
    private int id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private List<Booking> booking;
}
