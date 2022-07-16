package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.proxy.CloudProxy;
import com.ws.masterserver.service.AdminUserInfoService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.user.AdminUserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserInfoServiceImpl implements AdminUserInfoService {
    private final CloudProxy cloudProxy;
    private final WsRepository repository;

    @Override
    public Object create(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl create start");
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validCreate(dto);
        CompletableFuture<String> future = null;
        if (!StringUtils.isNullOrEmpty(dto.getAvatar())) {
            future = CompletableFuture.supplyAsync(() -> cloudProxy.uploadImage(dto.getAvatar()));
        }
        try {
            PasswordEncoder passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
            var user = UserEntity.builder()
                    .id(UidUtils.generateUid())
                    .firstName(dto.getFirstName().trim())
                    .lastName(dto.getLastName().trim())
                    .email(dto.getEmail().trim())
                    .phone(dto.getPhone().trim())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .avatar(future != null ? future.get() : null)
                    .active(true)
                    .gender(dto.getGender())
                    .role(RoleEnum.valueOf(dto.getRole()))
                    .dob(dto.getDob())
                    .build();
            log.info("AdminUserInfoServiceImpl create user before save: {}", JsonUtils.toJson(user));
            repository.userRepository.save(user);
            log.info("AdminUserInfoServiceImpl create user after save: {}", JsonUtils.toJson(user));
            return ResData.ok(user.getId());
        } catch (Exception e) {
            log.error("AdminUserInfoServiceImpl create error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }
}
