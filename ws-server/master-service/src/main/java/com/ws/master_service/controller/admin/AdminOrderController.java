package com.ws.master_service.controller.admin;

import com.ws.master_service.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.master_service.dto.admin.order.detail.DetailRes;
import com.ws.master_service.dto.admin.order.search.OrderReq;
import com.ws.master_service.dto.admin.order.search.OrderRes;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
@Slf4j
public class AdminOrderController extends WsController {

    @PostMapping("/search")
    @Operation(summary = "API danh sách hóa đơn")
    public ResponseEntity<PageData<OrderRes>> search4Admin(@RequestBody OrderReq req) {
        return ResponseEntity.ok(service.orderService.search4Admin(getCurrentUser(), req));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "API chi tiet hoa don")
    public ResponseEntity<ResData<DetailRes>> detail4Admin(@PathVariable String id) {
        return ResponseEntity.ok(service.orderService.detail4Admin(getCurrentUser(), id));
    }

    @PostMapping("/change-status")
    @Operation(summary = "API thay đổi trạng thái đơn hàng")
    public ResponseEntity<ResData<String>> changeStatus4Admin(@RequestBody ChangeStatusDto dto) {
        log.info("START API /api/v1/order//admin/change-status with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.orderService.changeStatus4Admin(getCurrentUser(), dto));
    }


}
