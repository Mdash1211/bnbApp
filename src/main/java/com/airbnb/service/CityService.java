package com.airbnb.service;

import com.airbnb.entity.City;

import java.util.List;

public interface CityService {
   public  City addCity(City city);

   public List<City> getAllCities();

   public City getCityById(Long id);

   public City updateCity(Long id, City city);

   public String deleteCity(Long id);
}
