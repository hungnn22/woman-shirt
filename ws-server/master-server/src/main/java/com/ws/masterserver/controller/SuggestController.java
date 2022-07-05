package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.suggest.SizeDto;
import com.ws.masterserver.utils.base.WsService;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/suggest")
@RequiredArgsConstructor
public class SuggestController {

    private final WsService service;

    @GetMapping("/category-list")
    public ResponseEntity<Object> getCategories() {
        return ResponseEntity.ok(ResData.ok(service.suggestService.getCategories()));
    }

    @GetMapping("/size")
    public ResponseEntity<Object> getSizeAvailable(SizeDto dto) {
        return ResponseEntity.ok(service.suggestService.getSizeAvailable(dto));
    }

}
