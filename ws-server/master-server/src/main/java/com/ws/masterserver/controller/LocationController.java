package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
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
