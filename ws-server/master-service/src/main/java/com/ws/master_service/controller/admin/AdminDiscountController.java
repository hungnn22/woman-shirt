package com.ws.master_service.controller.admin;

import com.ws.master_service.dto.admin.discount.create.DiscountDto;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/discount")
@RequiredArgsConstructor
@Slf4j
public class AdminDiscountController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "tao moi ma KM")
    public ResponseEntity<Object> create(@RequestBody DiscountDto payload) {
      log.info("start api /api/v1/admin/discount/create with payload: {}", JsonUtils.toJson(payload));
      return ResponseEntity.ok(service.discountService.create(getCurrentUser(), payload));
    }

}
