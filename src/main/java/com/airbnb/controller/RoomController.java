package com.airbnb.controller;

import com.airbnb.entity.Room;
import com.airbnb.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create/{propertyId}")
    public ResponseEntity<Room> createRoom(@PathVariable Long propertyId, @RequestBody Room room) {
        Room createdRoom = roomService.createRoom(propertyId, room);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    @PutMapping("/updateRoom/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return new ResponseEntity<>(roomService.updateRoom(id, room), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom/{id}/{propertyId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id,@PathVariable Long propertyId) {
        roomService.deleteRoom(id,propertyId);
        return new ResponseEntity<>("Room deleted!", HttpStatus.OK);
    }

    @GetMapping("/getPropertyRooms/{propertyId}")
    public ResponseEntity<List<Room>> getAllRoomsForProperty(@PathVariable Long propertyId) {
        return new ResponseEntity<>(roomService.getAllRoomsForProperty(propertyId), HttpStatus.OK);
    }

}