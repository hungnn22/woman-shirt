package com.ws.masterserver.utils.base;

import com.ws.masterserver.repository.*;
import com.ws.masterserver.repository.custom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WsRepository {
    public final AddressRepository addressRepository;
    public final CategoryRepository categoryRepository;
    public final ColorRepository colorRepository;
    public final EditHistoryRepository editHistoryRepository;
    public final EmailLogRepository emailLogRepository;
    public final MaterialRepository materialRepository;
    public final OrderRepository orderRepository;
    public final OrderDetailRepository orderDetailRepository;
    public final OrderStatusRepository orderStatusRepository;
    public final ProductRepository productRepository;
    public final ProductOptionRepository productOptionRepository;
    public final LocationRepository locationRepository;
    public final ResetTokenRepository resetTokenRepository;
    public final ReviewMediaRepository reviewMediaRepository;
    public final ReviewRepository reviewRepository;
    public final TypeRepository typeRepository;
    public final UserRepository userRepository;
    public final CartRepository cartRepository;
    public final NotificationRepository notificationRepository;
    public final UserNotificationRepository userNotificationRepository;
    public final SizeRepository sizeRepository;
    public final SuggestRepository suggestRepository;
    public final BodyHeightRepository bodyHeightRepository;
    public final BodyWeightRepository bodyWeightRepository;
    public final DiscountRepository discountRepository;
    public final DiscountCategoryRepository discountCategoryRepository;
    public final DiscountCustomerTypeRepository discountCustomerTypeRepository;
    public final DiscountProductRepository discountProductRepository;
    public final OrderDiscountRepository orderDiscountRepository;

    /** custom */
    public final CategoryCustomRepository categoryCustomRepository;
    public final OrderCustomRepository orderCustomRepository;
    public final OrderDetailCustomRepository orderDetailCustomRepository;
    public final ProductCustomRepository productCustomRepository;

}
