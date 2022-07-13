package com.ws.masterserver.utils.base;

import com.ws.masterserver.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WsService {
    public final AddressService addressService;
    public final CategoryService categoryService;
    public final ColorService colorService;
    public final MaterialService materialService;
    public final EditHistoryService editHistoryService;
    public final EmailLogService emailLogService;
    public final OrderService orderService;
    public final OrderDetailService orderDetailService;
    public final OrderStatusService orderStatusService;
    public final ProductOptionService productOptionService;
    public final ProductService productService;
    public final ResetTokenService resetTokenService;
    public final ReviewMediaService reviewMediaService;
    public final ReviewService reviewService;
    public final UserService userService;
    public final CartService cartService;
    public final SizeService sizeService;
    public final SuggestService suggestService;
    public final DashboardService dashboardService;
    public final NotificationService notificationService;
    public final AdminProductSearchService adminProductSearchService;
    public final MailService mailService;
    public final AdminUserSearchService adminUserSearchService;
}
