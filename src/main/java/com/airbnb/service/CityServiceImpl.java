package com.airbnb.service;

import com.airbnb.entity.City;
import com.airbnb.exception.CityNotFoundException;
import com.airbnb.repository.CityRepository;
import com.airbnb.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City addCity(City city) {
        if (city.getName() == null || city.getName().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty");
        }
        return cityRepository.save(city);
    }

    @Override
    public List<City> getAllCities() {
        List<City> cityList = cityRepository.findAll();
        if (cityList.isEmpty()) {
            throw new IllegalArgumentException("City list not found");
        }
        return cityList;
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City with ID " + id + " not found"));
    }

    @Override
    public City updateCity(Long id, City city) {
        City existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City with ID " + id + " not found"));
        existingCity.setName(city.getName());
        return cityRepository.save(existingCity);
    }

    @Override
    public String deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City with ID " + id + " not found"));
        cityRepository.deleteById(city.getId());
         return null;
    }
}
