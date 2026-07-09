package com.hotel.Rise.Security.User;

import com.hotel.Rise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hotel.Rise.models.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class roomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository
                .findByEmail(username)).orElseThrow(() -> new UsernameNotFoundException("Username/Email not found"));

        return  roomUserDetails.buildUserDetails(user);
    }
}
