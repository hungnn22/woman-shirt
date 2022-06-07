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
    public final ProductOptionRepository productOptionRepository;
    public final ResetTokenRepository resetTokenRepository;
    public final ReviewMediaRepository reviewMediaRepository;
    public final ReviewRepository reviewRepository;
    public final UserRepository userRepository;

    /** custom */
    public final CategoryCustomRepository categoryCustomRepository;
    public final OrderCustomRepository orderCustomRepository;
    public final OrderDetailCustomRepository orderDetailCustomRepository;
    public final ProductCustomRepository productCustomRepository;
}
