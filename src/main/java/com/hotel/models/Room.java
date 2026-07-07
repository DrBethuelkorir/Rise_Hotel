package com.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    private int id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;

    @OneToMany()
    Booking booking;
}
