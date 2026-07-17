    package com.hotel.Rise.contollers;

    import com.hotel.Rise.Service.Booking.IBookingService;
    import com.hotel.Rise.dtos.Response;
    import com.hotel.Rise.models.Booking;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("bookings")
    public class BookingController {

        private final IBookingService bookingService;

        @PostMapping("/book-room/{roomId}/{userId}")
        @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
        public ResponseEntity<Response> createBooking(@PathVariable Long roomId, @PathVariable Long userId, @RequestBody Booking booking){
            Response response =bookingService.saveBooking(roomId,userId,booking);
            response.setMessage("Booking created successfully");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        @GetMapping("/AllBookings")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Response> retrieveAllBookings(){
            Response response = bookingService.getAllBookings();
            response.setMessage("All bookings retrieved");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        @GetMapping("/get-Booking-byConfirmationCode/{confirmationCode}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Response> retrieveBookingByConfirmationCode(@PathVariable String confirmationCode){
            Response response = bookingService.findBookingsByConfirmationCode(confirmationCode);
            response.setMessage("Bookings retrieved successfully");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        @DeleteMapping("/delete/{bookingId}")
        @PreAuthorize("hasAuthority('ADMIN') or @bookingSecurity.isBookingOwner(#bookingId, authentication.principal)")

        public ResponseEntity<Response> deleteBooking(@PathVariable Long bookingId){
            Response response = bookingService.deleteBooking(bookingId);
            response.setMessage("Booking deleted successfully");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

    }
