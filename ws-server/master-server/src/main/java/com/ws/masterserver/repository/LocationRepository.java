package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.location.LocationDto;
import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,String> {
//    @Query(value = "select * from location",nativeQuery = true)
//@Query("SELECT t FROM LocationEntity t")

    @Query("select new com.ws.masterserver.dto.customer.location.LocationDto(\n" +
            "c.id,\n" +
            "c.addressName,\n" +
            "c.hotline,\n" +
            "c.addressLink,\n" +
            "c.directLink) from LocationEntity c\n")
    List<LocationDto> findAllLocations();



}
