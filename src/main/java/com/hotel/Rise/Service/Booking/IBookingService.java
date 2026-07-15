package com.hotel.Rise.Service.Booking;

import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.models.Booking;

public interface IBookingService {
    Response saveBooking(Long roomID, Long userID, Booking booking);
    Response deleteBooking(Long bookingId);
    Response getAllBookings();
    Response findBookingsByConfirmationCode(String confirmationCode);
}
