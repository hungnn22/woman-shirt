package com.ws.master_service.repository;

import com.ws.master_service.dto.customer.location.LocationDto;
import com.ws.master_service.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,String> {
//    @Query(value = "select * from location",nativeQuery = true)
//@Query("SELECT t FROM LocationEntity t")

    @Query("select new com.ws.master_service.dto.customer.location.LocationDto(\n" +
            "c.id,\n" +
            "c.addressName,\n" +
            "c.hotline,\n" +
            "c.addressLink) from LocationEntity c\n")
    List<LocationDto> findAllLocations();



}
