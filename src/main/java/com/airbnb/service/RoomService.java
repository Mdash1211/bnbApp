package com.airbnb.service;

import com.airbnb.entity.Room;
import java.util.List;

public interface RoomService {
    public Room createRoom(Long propertyId, Room room);

    public Room updateRoom(Long id, Room room);

    public void deleteRoom(Long id,Long propertyId);

    public List<Room> getAllRoomsForProperty(Long propertyId);
}
