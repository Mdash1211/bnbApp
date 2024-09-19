package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Country> addCountry(@RequestBody Country country){
        return new ResponseEntity<>(countryService.addCountry(country), HttpStatus.CREATED);
    }
    @GetMapping("/getAllCountries")
    public ResponseEntity<List<Country>> getAllCountries(){
        return new ResponseEntity<>(countryService.getAllCountries(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country) {
        Country updatedCountry = countryService.updateCountry(id, country);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id) {
         countryService.deleteCountry(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);

    }

}
