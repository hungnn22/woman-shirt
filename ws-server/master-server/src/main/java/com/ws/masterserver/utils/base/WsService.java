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
    public final LocationService locationService;
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
    public final AdminProductService adminProductService;
    public final MailService mailService;
    public final AdminUserService adminUserService;
    public final AdminUserInfoService adminUserInfoService;
    public final UserInfoService userInfoService;
    public final AdminReportService adminReportService;
    public final AdminDiscountService adminDiscountService;
    public final DiscountCategoryService discountCategoryService;
    public final DiscountCustomerTypeService discountCustomerTypeService;
    public final DiscountProductService discountProductService;
    public final OrderDiscountService orderDiscountService;
}
