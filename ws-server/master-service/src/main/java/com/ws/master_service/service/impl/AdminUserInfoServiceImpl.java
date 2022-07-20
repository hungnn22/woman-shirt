package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.user.info.UserDto;
import com.ws.master_service.entity.UserEntity;
import com.ws.master_service.service.AdminUserInfoService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.*;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.validator.AuthValidator;
import com.ws.master_service.utils.validator.user.AdminUserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserInfoServiceImpl implements AdminUserInfoService {
    private final WsRepository repository;

    @Override
    public Object create(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl create start");
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validCreate(dto);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
        var user = UserEntity.builder()
                .id(UidUtils.generateUid())
                .firstName(dto.getFirstName().trim())
                .lastName(dto.getLastName().trim())
                .email(dto.getEmail().trim())
                .phone(dto.getPhone().trim())
                .password(passwordEncoder.encode(dto.getPassword()))
                .active(true)
                .gender(dto.getGender())
                .role(RoleEnum.valueOf(dto.getRole()))
                .dob(dto.getDob())
                .build();
        this.saveUser(user);
        return ResData.ok(user.getId());
    }

    @Override
    public Object update(CurrentUser currentUser, UserDto dto) {
        log.info("AdminUserInfoServiceImpl update start dto: {}", JsonUtils.toJson(dto));
        AuthValidator.checkAdmin(currentUser);
        AdminUserValidator.validUpdate(dto);
        var user = repository.userRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new WsException(WsCode.USER_NOT_FOUND);
        });
        user.setFirstName(dto.getFirstName().trim())
                .setLastName(dto.getLastName().trim())
                .setPhone(dto.getPhone().trim())
                .setRole(RoleEnum.valueOf(dto.getRole()))
                .setGender(dto.getGender())
                .setDob(dto.getDob());
        this.saveUser(user);
        return ResData.ok(user.getId());
    }

    @Override
    public Object changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        var user = repository.userRepository.findById(id).orElseThrow(() -> {
            throw new WsException(WsCode.USER_NOT_FOUND);
        });
        if (currentUser.getId().equals(user.getId())) {
            throw new WsException(WsCode.DONT_CHANGE_YOURSELF);
        }
        if (RoleEnum.ROLE_ADMIN.equals(user.getRole())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        user.setActive(!user.getActive());
        this.saveUser(user);
        return ResData.ok(user.getId());
    }

    private void saveUser(UserEntity user) {
        log.info("AdminUserInfoServiceImpl user before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("AdminUserInfoServiceImpl user after save: {}", JsonUtils.toJson(user));
    }
}
