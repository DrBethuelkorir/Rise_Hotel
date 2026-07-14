    package com.hotel.Rise.utils;

    import com.hotel.Rise.dtos.BookingDto;
    import com.hotel.Rise.dtos.RoomDto;
    import com.hotel.Rise.dtos.UserDto;
    import com.hotel.Rise.models.Booking;
    import com.hotel.Rise.models.Room;
    import com.hotel.Rise.models.User;

    import java.security.SecureRandom;
    import java.util.List;
    import java.util.stream.Collectors;


    public class Utils {
        private static final String Alpha_numeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static final SecureRandom secureRandom = new SecureRandom();

        public static String generateString(int length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int randomIndex =  secureRandom.nextInt(Alpha_numeric.length());
                char randomChar =  Alpha_numeric.charAt(randomIndex);
                sb.append(randomChar);
            }
            return sb.toString();
        }

        public static UserDto mapUserEntityToUserDTO(User user){
            UserDto userDto = new UserDto();

            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setRole(user.getRole());
            return userDto;
        }

        public static UserDto mapUserEntityToUserDTOPlusBooking(User user){
            UserDto userDto = new UserDto();

            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setRole(user.getRole());

            if(!user.getBookings().isEmpty()){
                List<BookingDto> bookingDtos = user.getBookings().stream()
                        .map(booking ->mapBookingEntityToBookingDTO(booking))
                        .collect(Collectors.toList());
                userDto.setBookings(bookingDtos);
            }

            return userDto;
        }
        public static BookingDto mapBookingEntityToBookingDTO(Booking booking){
            BookingDto bookingDto = new BookingDto();

            bookingDto.setId(booking.getId());
            bookingDto.setCheckInDate(booking.getCheckInDate());
            bookingDto.setCheckOutDate(booking.getCheckOutDate());
            bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
            bookingDto.setNumOfChildren(booking.getNumOfChildren());
            bookingDto.setNumOfAdults(booking.getNumOfAdults());

            return bookingDto;
        }

        public static BookingDto mapBookingEntityToBookingDTOPlusBooking(Booking booking, boolean mapUser){
            BookingDto bookingDto = new BookingDto();

            bookingDto.setId(booking.getId());
            bookingDto.setCheckInDate(booking.getCheckInDate());
            bookingDto.setCheckOutDate(booking.getCheckOutDate());
            bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
            bookingDto.setNumOfChildren(booking.getNumOfChildren());
            bookingDto.setNumOfAdults(booking.getNumOfAdults());

            if(mapUser){
                bookingDto.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
            }
            if(booking.getRoom() != null){
                RoomDto roomDto = new RoomDto();
                roomDto.setId(booking.getRoom().getId());
                roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
                roomDto.setRoomType(booking.getRoom().getRoomType());
                roomDto.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());

                bookingDto.setRoom(roomDto);
            }
            return bookingDto;
        }
        public static RoomDto mapRoomEntityToRoomDTO(Room room){
            RoomDto roomDto = new RoomDto();
            roomDto.setId(room.getId());
            roomDto.setRoomPrice(room.getRoomPrice());
            roomDto.setRoomType(room.getRoomType());
            roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());

            return roomDto;
        }

        public static RoomDto mapRoomEntityToRoomDTOPlusBooking(Room room){

            RoomDto roomDto = new RoomDto();

            roomDto.setId(room.getId());
            roomDto.setRoomPrice(room.getRoomPrice());
            roomDto.setRoomType(room.getRoomType());
            roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());

            if(room.getBooking() != null &&  !room.getBooking().isEmpty()){
                roomDto.setBooking(room.getBooking().stream()
                        .map(booking ->mapBookingEntityToBookingDTO(booking))
                        .collect(Collectors.toList()));
            }

            return roomDto;
        }

        public static List<RoomDto> mapRoomListEntityToRoomListDTO(List<Room> roomsList){
            return roomsList.stream().map(r -> mapRoomEntityToRoomDTO(r)).collect(Collectors.toList());
        }
        public static List<UserDto> mapUserListEntityToUserDTO(List<User> userList){
            return userList.stream().map(user -> mapUserEntityToUserDTO(user)).collect(Collectors.toList());
        }
        public static List<BookingDto> mapBookingListEntityToBookingDTO(List<Booking> bookingList){
            return bookingList.stream().map(b -> mapBookingEntityToBookingDTO(b)).collect(Collectors.toList());
        }
    }
