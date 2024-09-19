package com.airbnb.service;

import com.airbnb.entity.Property;

import java.util.List;

public interface PropertyService {
     public Property addProperty(Property property);

     public Property updateProperty(Long id, Property property);

     public String deleteProperty(Long id);

     public List<Property> searchPropertyByCity(String cityName);

     public List<Property> searchPropertyByCountry(String countryName);

     public List<Property> searchByCityOrCountry(String cityOrCountryName);
}
