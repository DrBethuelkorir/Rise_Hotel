package com.hotel.Rise.Service.Booking;

import com.hotel.Rise.Exeption.OurExeption;
import com.hotel.Rise.Service.Room.RoomService;
import com.hotel.Rise.dtos.BookingDto;
import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.models.Booking;
import com.hotel.Rise.models.Room;
import com.hotel.Rise.models.User;
import com.hotel.Rise.repository.BookingRepository;
import com.hotel.Rise.repository.RoomRepository;
import com.hotel.Rise.repository.UserRepository;
import com.hotel.Rise.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public Response saveBooking(Long roomID, Long userID, Booking booking) {
        Response response = new Response();
        try {
            if(booking.getCheckOutDate().isBefore(booking.getCheckInDate())){
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }
            Room room  = roomRepository.findById(roomID).orElseThrow(() -> new OurExeption("Room nor found"));
            User user = userRepository.findById(userID).orElseThrow(() -> new OurExeption("User not found"));

            List<Booking> existingBookings = room.getBooking();
            if(!roomAvailable(booking,existingBookings)){
                throw new OurExeption("booking is not available");
            }
            booking.setRoom(room);
            booking.setUser(user);
            String confirmationCode = Utils.generateString(10);
            booking.setBookingConfirmationCode(confirmationCode);
            bookingRepository.save(booking);
            response.setStatusCode(200);
            response.setMessage("booking is saved successfully");
            response.setConfirmationCode(confirmationCode);
        } catch (IllegalArgumentException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (OurExeption e) {
            response.setStatusCode(500);
            response.setMessage("Error saving booking" +e.getMessage());
        }
        return response;
    }

    private boolean roomAvailable(Booking booking, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        booking.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                booking.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
                );
    }

    @Override
    public Response deleteBooking(Long bookingId) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new OurExeption("Booking not found"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("booking is deleted successfully");
        } catch (OurExeption e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting booking" +e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try {
            List<Booking> bookings = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "Id"));
            List<BookingDto> bookingDto = Utils.mapBookingListEntityToBookingDTO(bookings);
            response.setStatusCode(200);
            response.setBookingList(bookingDto);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response findBookingsByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurExeption("Booking not found"));
            BookingDto bookingDto = Utils.mapBookingEntityToBookingDTO(booking);
            response.setStatusCode(200);
            response.setBooking(bookingDto);
        } catch (IllegalArgumentException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (OurExeption e) {
            response.setStatusCode(500);
            response.setMessage("Error finding a booking" +e.getMessage());
        }
        return response;
    }
}
