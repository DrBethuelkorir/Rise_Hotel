        package com.hotel.Rise.Service.User;

        import com.hotel.Rise.Exeption.OurExeption;
        import com.hotel.Rise.Security.Utils.JWTUtils;
        import com.hotel.Rise.dtos.LoginDto;
        import com.hotel.Rise.dtos.Response;
        import com.hotel.Rise.dtos.UserDto;
        import com.hotel.Rise.models.User;
        import com.hotel.Rise.repository.UserRepository;
        import com.hotel.Rise.utils.Utils;
        import lombok.RequiredArgsConstructor;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;

        import java.util.List;

        @Service
        @RequiredArgsConstructor
        public class UserService implements IUserService{

            private final UserRepository userRepository;
            private final PasswordEncoder passwordEncoder;
            private final JWTUtils jwtUtils;
            private final AuthenticationManager authenticationManager;




            @Override
            public Response registerUser(User user) {

                Response response = new Response();

                try {
                    if(user.getRole() == null || user.getRole().isBlank() ){
                        user.setRole("USER");
                    }
                    if(userRepository.existsByEmail(user.getEmail())){
                        throw new OurExeption(user.getEmail() + "Already Exists");
                    }
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    User savedUSer = userRepository.save(user);
                    UserDto userDto = Utils.mapUserEntityToUserDTO(savedUSer);
                    response.setStatusCode(200);
                    response.setUser(userDto);

                } catch (OurExeption e) {
                    response.setStatusCode(400);
                    response.setMessage(e.getMessage());
                }catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED DURING USER REGISTRATION" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response loginUser(LoginDto loginDto) {

                Response response = new Response();

                try {
                    Authentication authentication = authenticationManager.authenticate(
                             new UsernamePasswordAuthenticationToken(
                                     loginDto.getEmail(),
                                     loginDto.getPassword()
                             )
                     );

                    User user = userRepository.findByEmail(loginDto.getEmail())
                            .orElseThrow(() -> new OurExeption("User not found"));

                    String token = jwtUtils.generateToken(authentication);
                    response.setStatusCode(200);
                    response.setToken(token);
                    response.setMessage("success");
                    response.setRole(authentication.getAuthorities().toString());
                    response.setExpirationTime("7 days");
                } catch (OurExeption e) {
                    response.setStatusCode(404);
                    response.setMessage(e.getMessage());
                }
                catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED DURING USER REGISTRATION" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response getUserBookingHistory(Long userId) {
                Response response = new Response();

                try {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new OurExeption("User not found"));
                    UserDto userDto = Utils.mapUserEntityToUserDTOPlusBooking(user);
                    response.setStatusCode(200);
                    response.setUser(userDto);
                    response.setMessage("success");
                } catch (OurExeption e) {
                    response.setStatusCode(404);
                    response.setMessage(e.getMessage());
                }
                catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response getAllUsers() {

                Response response = new Response();

                try {
                    List<User> userlist = userRepository.findAll();
                    List<UserDto> userDtoList = Utils.mapUserListEntityToUserDTO(userlist);
                    response.setStatusCode(200);
                    response.setMessage("success");
                    response.setUsersList(userDtoList);

                } catch (Exception e) {
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED DURING USER LIST" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response deleteUSer(Long userId) {

                Response response = new Response();

                try {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new OurExeption("User not found")) ;
                    userRepository.deleteById(userId);
                    response.setStatusCode(200);
                    response.setMessage("deleted success");
                } catch (OurExeption e) {
                    response.setStatusCode(404);
                    response.setMessage(e.getMessage());
                }
                catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response getUserById(Long userId) {
                Response response = new Response();

                try {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new OurExeption("User not found"));
                    UserDto userDto = Utils.mapUserEntityToUserDTO(user);
                    response.setStatusCode(200);
                    response.setUser(userDto);
                } catch (OurExeption e) {
                    response.setStatusCode(404);
                    response.setMessage(e.getMessage());
                }
                catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED" + e.getMessage());
                }
                return response;
            }

            @Override
            public Response getMyInfo(String email) {
                Response response = new Response();

                try {
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new OurExeption("User not found"));
                    UserDto userDto = Utils.mapUserEntityToUserDTO(user);
                    response.setStatusCode(200);
                    response.setUser(userDto);
                } catch (OurExeption e) {
                    response.setStatusCode(404);
                    response.setMessage(e.getMessage());
                }catch (Exception e){
                    response.setStatusCode(500);
                    response.setMessage("ERROR OCCURRED" + e.getMessage());
                }
                return response;
            }
        }
