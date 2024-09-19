package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.exception.PropertyNotFoundException;
import com.airbnb.exception.RoomNotFoundException;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl  implements BookingService{

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.roomRepository = roomRepository;
    }

   @Override
   @Transactional
   public Booking createBooking(Long propertyId, String roomType, Booking booking, AppUser user) {
       Property property = propertyRepository.findById(propertyId).orElseThrow(
               () -> new PropertyNotFoundException("Property Not Found")
       );

       List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate())
               .collect(Collectors.toList());

       List<Room> rooms = new ArrayList<>();
       for (LocalDate date : datesBetween) {
           Optional<Room> optionalRoom = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, roomType, date);

           Room room = optionalRoom.orElseThrow(
                   () -> new RoomNotFoundException("Room Not Found for the given date: " + date)
           );

           if (room.getCount() == 0) {
               throw new RoomNotFoundException("No rooms available for the given date: " + date);
           }
           rooms.add(room);
       }
       float total = 0;
       for (Room room : rooms) {
           total += room.getPrice();
       }
       booking.setTotalPrice(total);
       booking.setProperty(property);
       booking.setAppUser(user);
       booking.setTypeOfRoom(roomType);
       Booking savedBooking = bookingRepository.save(booking);

       if (savedBooking != null) {
           for (Room room : rooms) {
               room.setCount(room.getCount() - 1);
               roomRepository.save(room);
           }
       }
       return savedBooking;

}

    public static Stream<LocalDate> getDatesBetween(LocalDate checkInDate, LocalDate checkOutDate) {
    LocalDate startDate = checkInDate;
    LocalDate endDate = checkOutDate.plusDays(1);
    return Stream.iterate(startDate, date -> date.plusDays(1))
            .limit(endDate.toEpochDay() - startDate.toEpochDay() + 1);
    }
}






//       Property property = propertyRepository.findById(propertyId).orElseThrow(
//               ()->new PropertyNotFoundException("Property Not Found")
//       );
////     List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate());
//       List<LocalDate> datesBetween = getDatesBetween(booking.getCheckInDate(), booking.getCheckOutDate()).collect(Collectors.toList());
//       List<Room> rooms=new ArrayList<>();
//       for(LocalDate date:datesBetween){
//           Room room = roomRepository.findByPropertyIdAndTypeAndDate(propertyId, roomType,date);
//           if (room.getCount() == 0) {
//               throw new RoomNotFoundException("Room Not Found");
//           }
//           rooms.add(room);
//       }
//       float total=0;
//       for(Room room:rooms){
//           total+=room.getPrice();
//       }
//       booking.setTotalPrice(total);
//       booking.setProperty(property);
//       booking.setAppUser(user);
//       booking.setTypeOfRoom(roomType);
//       Booking savedBooking=bookingRepository.save(booking);
//
//       if (savedBooking != null) {
//           for(Room room : rooms) {
//               room.setCount(room.getCount() - 1);
//               roomRepository.save(room);
//           }
//       }
//       return savedBooking;






//    public static List<LocalDate> getDatesBetween(LocalDate checkInDate, LocalDate checkOutDate){
//        List<LocalDate> dateList = new ArrayList<>();
//        LocalDate currentDate = checkInDate;
//
//        while (!currentDate.isAfter(checkOutDate)) {
//            dateList.add(currentDate);
//            currentDate = checkInDate.plusDays(1);
//        }
//        return dateList;
//    }
