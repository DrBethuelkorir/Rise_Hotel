package com.hotel.Rise.models;

import jakarta.persistence.*;
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
@Table(name ="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String description;

    @OneToMany(mappedBy = "room",orphanRemoval = true,cascade = CascadeType.ALL)
    List<Booking> booking = new ArrayList<>();
}
