package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.entity.Room;
import com.airbnb.exception.PropertyNotFoundException;
import com.airbnb.exception.RoomNotFoundException;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService  {

    private RoomRepository roomRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional
    public Room createRoom(Long propertyId, Room room) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new PropertyNotFoundException("Property does not exist")
        );
        room.setProperty(property);
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Property property = propertyRepository.findById(room.getProperty().getId())
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new RoomNotFoundException("Room not found with id:" + id)
        );
            existingRoom.setCount(room.getCount());
            existingRoom.setPrice(room.getPrice());
            existingRoom.setType(room.getType());
            return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id,Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new RoomNotFoundException("Room not found with id:" + id)
        );
        roomRepository.delete(room);
    }

    @Override
    public List<Room> getAllRoomsForProperty(Long propertyId) {
        return roomRepository.findRoomsByPropertyId(propertyId);
    }
}
