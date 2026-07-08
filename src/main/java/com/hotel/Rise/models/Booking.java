package com.hotel.Rise.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Future(message = "checkInDate should not be blank")
    private Date checkInDate;
    @Future(message = "checkOutDate should not be blank")
    private Date checkOutDate;
    @Min(value = 1,message = "Number of adults cannot be less than one")
    private Long numOfAdults;
    @Min(value = 0,message = "Number of children cannot be less than one")
    private Long numOfChildren;
    private Long numOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    Room room;

    public void calculateNumberOfGuest(){
        this.numOfGuests = this.numOfChildren + this.numOfAdults;
    }

    public void setNumOfAdults(Long numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateNumberOfGuest();
    }

    public void setNumOfChildren(Long numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateNumberOfGuest();
    }
}
