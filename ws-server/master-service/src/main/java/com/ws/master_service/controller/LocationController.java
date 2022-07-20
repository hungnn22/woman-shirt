package com.ws.master_service.controller;

import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.base.rest.ResData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
@Slf4j

public class LocationController extends WsController {

    @GetMapping("/find-location")
    public ResponseEntity<Object> findLocation(){
        return ResponseEntity.ok(ResData.ok(service.locationService.findLocations()));
    }

}
