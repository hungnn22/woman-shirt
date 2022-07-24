package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.user.register.RegisterDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.service.CustomerDetailService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.enums.DiscountSuperTypeEnums;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.user.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService {
    private static final String CONTENT = "Cảm ơn bạn đã đăng ký tài khoản tại Woman-Shirt. Chúng tôi xin gửi tặng bạn Mã khuyến mãi: %s(%s)";
    private static final String TEMPLATE = "Cảm ơn bạn đã đăng ký tài khoản tại <b>Woman-Shirt</b>. Chúng tôi xin gửi tặng bạn Mã khuyến mãi: <b>%s</b> (%s)";

    private final WsRepository repository;

    /**
     * KH đăng ký tài khoản
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Object register(RegisterDto dto) {
        RegisterValidator.validDtoData(dto);
        RegisterValidator.validDtoConstrains(dto, repository);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        var user = UserEntity.builder()
                .id(UidUtils.generateUid())
                .role(RoleEnum.ROLE_CUSTOMER)
                .active(true)
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .dob(DateUtils.toDate(dto.getDob(), DateUtils.F_DDMMYYYY))
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone().trim())
                .email(dto.getEmail().trim())
                .build();
        log.info("register() user before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("register() user after save: {}", JsonUtils.toJson(user));

        // TODO: 24/07/2022: Sau khi KH đăng kí tài khoản thành công, hệ thống sẽ tự động gửi notification + email với
        //  nội dụng tặng mã Khuyến mãi cho KH mới. Sử dụng đa luồng và chỉ ghi lại log(không throw exception)
        /**
         * get mã KM giành cho những KH mới
         * */
//        var discount4NewCustomer = repository.discountRepository.findByDiscountTypeName4NewCustomer(DiscountSuperTypeEnums.NEW_CUSTOMER.name());
//        CompletableFuture sendMailCF = CompletableFuture.runAsync(() -> {
//        });
//        CompletableFuture saveNotificationCF = CompletableFuture.runAsync(() -> {
//
//        });
//        try {
//            CompletableFuture.allOf(sendMailCF, saveNotificationCF).get();
//        } catch (Exception e) {
//            log.error("register() error: {}", e.getMessage());
//        }

        return ResData.ok(user.getId());
    }
}
