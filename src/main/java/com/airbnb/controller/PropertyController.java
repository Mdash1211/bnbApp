package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property createdProperty = propertyService.addProperty(property);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }
    @PutMapping("/updateProperty")
    public ResponseEntity<Property> updateProperty(@RequestParam Long id, @RequestBody Property property){
        return new ResponseEntity<>(propertyService.updateProperty(id,property),HttpStatus.OK);
    }
    @DeleteMapping("/deleteProperty")
    public ResponseEntity<String> deleteProperty(@RequestParam Long id){
        String s=propertyService.deleteProperty(id);
        return new ResponseEntity<>("Property Deleted!",HttpStatus.OK);
    }
    @GetMapping("/searchPropertyByCity")
    public ResponseEntity<List<Property>> searchPropertyByCity(@RequestParam("city") String cityName){
        return new ResponseEntity<>(propertyService.searchPropertyByCity(cityName),HttpStatus.OK);
    }
    @GetMapping("/searchPropertyByCountry")
    public ResponseEntity<List<Property>> searchPropertyByCountry(@RequestParam("country") String countryName){
        return new ResponseEntity<>(propertyService.searchPropertyByCountry(countryName), HttpStatus.OK);
    }
    @GetMapping("/searchByCityOrCountry")
    public ResponseEntity<List<Property>> searchByCityOrCountry(@RequestParam("cityOrCountry") String cityOrCountryName){
        return new ResponseEntity<>(propertyService.searchByCityOrCountry(cityOrCountryName), HttpStatus.OK);
    }

}
