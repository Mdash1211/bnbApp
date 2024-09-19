package com.airbnb.service;

import com.airbnb.entity.Country;
import com.airbnb.exception.CountryNotFoundException;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl  implements CountryService {

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country addCountry(Country country) {
        if (country.getName() == null || country.getName().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be null or empty");
        }
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAllCountries() {

        List<Country> countryList = countryRepository.findAll();
        if (countryList.isEmpty()){
            throw new IllegalArgumentException("Country List not found");
        }
        return countryList;
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country with ID " + id + " not found"));
    }

    @Override
    public Country updateCountry(Long id, Country country) {
        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country with ID " + id + " not found"));
        existingCountry.setName(country.getName());
        return countryRepository.save(existingCountry);

    }

    @Override
    public String deleteCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country with ID " + id + " not found"));
            countryRepository.deleteById(country.getId());
            return "Country Deleted";
    }

}

