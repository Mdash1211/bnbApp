package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;

public interface BookingService {
    public Booking createBooking(Long propertyId, String roomType, Booking booking, AppUser user);
}
