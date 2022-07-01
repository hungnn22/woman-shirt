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
    public final OrderPromotionRepository orderPromotionRepository;
    public final OrderStatusRepository orderStatusRepository;
    public final ProductRepository productRepository;
    public final PromotionRepository promotionRepository;
    public final ProductOptionRepository productOptionRepository;
    public final PromotionTypeRepository promotionTypeRepository;
    public final ResetTokenRepository resetTokenRepository;
    public final ReviewMediaRepository reviewMediaRepository;
    public final ReviewRepository reviewRepository;
    public final TypeRepository typeRepository;
    public final UserRepository userRepository;
    public final CartRepository cartRepository;
    public final NotificationRepository notificationRepository;
    public final UserNotificationRepository userNotificationRepository;
    /** custom */
    public final CategoryCustomRepository categoryCustomRepository;
    public final OrderCustomRepository orderCustomRepository;
    public final OrderDetailCustomRepository orderDetailCustomRepository;
    public final ProductCustomRepository productCustomRepository;
}
