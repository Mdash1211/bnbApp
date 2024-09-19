package com.airbnb.repository;

import com.airbnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r JOIN r.property p WHERE p.id = :propertyId")
    List<Room> findRoomsByPropertyId(@Param("propertyId") Long propertyId);

    @Query("SELECT r FROM Room r WHERE r.property.id = :propertyId AND r.type = :type")
    Room findRoomsByPropertyIdAndType(@Param("propertyId") Long propertyId, @Param("type")String type);

//    @Query("SELECT r FROM Room r WHERE r.property.id = :propertyId AND r.type = :type AND r.date = :date")
//    Room findByPropertyIdAndTypeAndDate(
//            @Param("propertyId") Long propertyId,
//            @Param("type") String type,
//            @Param("date") LocalDate date
//    );

    @Query("SELECT r FROM Room r WHERE r.property.id = :propertyId AND r.type = :type AND r.date = :date")
    Optional<Room> findByPropertyIdAndTypeAndDate(
            @Param("propertyId") Long propertyId,
            @Param("type") String type,
            @Param("date") LocalDate date
    );

}