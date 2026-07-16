package com.hotel.Rise.contollers;

import com.hotel.Rise.Service.User.UserService;
import com.hotel.Rise.dtos.LoginDto;
import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody User user) {
        Response response = userService.registerUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
        Response response = userService.loginUser(loginDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
