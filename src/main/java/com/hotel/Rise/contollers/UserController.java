package com.hotel.Rise.contollers;

import com.hotel.Rise.Service.User.UserService;
import com.hotel.Rise.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-byid/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId) {
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> deleteUserById(@PathVariable Long userId) {
        Response response = userService.deleteUSer(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("get-user-bookingHistory/{userId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable Long userId) {
        Response response = userService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-logged-in-user/{}")
    public ResponseEntity<Response> getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
