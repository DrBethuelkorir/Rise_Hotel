package com.hotel.Rise.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name should not be blank")
    private String name;
    @NotBlank(message = "name should not be blank")
    private String email;
    @NotNull(message = "email should not be blank")
    private Long phoneNumber;
    @NotBlank(message = "password should not be blank")
    private String password;
    private String role;

    @OneToMany(mappedBy = "user",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Booking>  bookings = new ArrayList<>();
}
