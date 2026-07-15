package com.hotel.Rise.Service.User;

import com.hotel.Rise.dtos.LoginDto;
import com.hotel.Rise.dtos.Response;
import com.hotel.Rise.models.User;

public interface IUserService {
    Response registerUser(User user);
    Response loginUser(LoginDto loginDto);
    Response getUserBookingHistory(Long userId);
    Response getAllUsers();
    Response deleteUSer(Long userId);
    Response getUserById(Long userId);
    Response getMyInfo(String email);
}
