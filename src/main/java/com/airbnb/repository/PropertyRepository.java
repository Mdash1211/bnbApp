package com.airbnb.repository;

import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p JOIN p.city c WHERE c.name = :cityName")
    List<Property> searchByCityProperty(@Param("cityName") String cityName);

    @Query("SELECT p FROM Property p JOIN p.country c WHERE c.name = :countryName")
    List<Property> searchByCountryProperty(@Param("countryName") String countryName);

    @Query("SELECT p FROM Property p WHERE p.city.name = :cityOrCountry OR p.country.name=:cityOrCountry")
    List<Property> searchByCityOrCountry(@Param("cityOrCountry") String cityOrCountry);
    

    


}