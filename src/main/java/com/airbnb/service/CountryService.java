package com.airbnb.service;

import com.airbnb.entity.Country;

import java.util.List;

public interface CountryService {
     public Country addCountry(Country country);

      public List<Country> getAllCountries();

      public Country getCountryById(Long id);

      public Country updateCountry(Long id, Country country);

      public String deleteCountry(Long id);
}
