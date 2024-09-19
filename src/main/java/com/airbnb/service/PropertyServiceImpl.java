package com.airbnb.service;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.exception.CityNotFoundException;
import com.airbnb.exception.CountryNotFoundException;
import com.airbnb.exception.PropertyNotFoundException;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyServiceImpl  implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               CountryRepository countryRepository,
                               CityRepository cityRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }


    @Transactional
    @Override
    public Property addProperty(Property property) {
        Country country = countryRepository.findById(property.getCountry().getId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        City city = cityRepository.findById(property.getCity().getId())
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        property.setCountry(country);
        property.setCity(city);

        return propertyRepository.save(property);
    }

    @Transactional
    @Override
    public Property updateProperty(Long id, Property property) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        Country country = countryRepository.findById(property.getCountry().getId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        City city = cityRepository.findById(property.getCity().getId())
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        existingProperty.setName(property.getName());
        existingProperty.setNumberOfGuests(property.getNumberOfGuests());
        existingProperty.setNumberOfBeds(property.getNumberOfBeds());
        existingProperty.setNumberOfBedrooms(property.getNumberOfGuests());
        existingProperty.setNumberOfBathrooms(property.getNumberOfBathrooms());
        existingProperty.setCountry(country);
        existingProperty.setCity(city);

        return propertyRepository.save(existingProperty);
    }

    @Override
    public String deleteProperty(Long id) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found"));
        propertyRepository.delete(existingProperty);
        return null;
    }

    @Override
    public List<Property> searchPropertyByCity(String cityName) {
        List<Property> properties = propertyRepository.searchByCityProperty(cityName);
        return properties;
    }

    @Override
    public List<Property> searchPropertyByCountry(String countryName) {
        List<Property> byCountryProperty = propertyRepository.searchByCountryProperty(countryName);
        return byCountryProperty;
    }

    @Override
    public List<Property> searchByCityOrCountry(String cityOrCountryName) {
        return propertyRepository.searchByCityOrCountry(cityOrCountryName);
    }
}
