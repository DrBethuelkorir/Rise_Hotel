package com.hotel.Rise.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    private int id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;

    @OneToMany(mappedBy = "room",orphanRemoval = true,cascade = CascadeType.ALL)
    List<Booking> booking = new ArrayList<>();
}
