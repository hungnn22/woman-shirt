package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.proxy.CloudProxy;
import com.ws.masterserver.service.AdminUserInfoService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
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

    @Override
    public Object update(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl update start dto: {}", JsonUtils.toJson(dto));
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validUpdate(dto);
        CompletableFuture<String> future = null;
        if (!StringUtils.isNullOrEmpty(dto.getAvatar())) {
            future = CompletableFuture.supplyAsync(() -> cloudProxy.uploadImage(dto.getAvatar()));
        }
        var user = repository.userRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new WsException(WsCode.USER_NOT_FOUND);
        });
        try {
            user.setFirstName(dto.getFirstName().trim())
                    .setLastName(dto.getLastName().trim())
                    .setPhone(dto.getPhone().trim())
                    .setRole(RoleEnum.valueOf(dto.getRole()))
                    .setGender(dto.getGender())
                    .setAvatar(future != null ? future.get() : null)
                    .setDob(dto.getDob());
            log.info("AdminUserInfoServiceImpl update user before save: {}", JsonUtils.toJson(user));
            repository.userRepository.save(user);
            log.info("AdminUserInfoServiceImpl update user after save: {}", JsonUtils.toJson(user));
            return ResData.ok(user.getId());
        } catch (Exception e) {
            log.error("AdminUserInfoServiceImpl update error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }
}
