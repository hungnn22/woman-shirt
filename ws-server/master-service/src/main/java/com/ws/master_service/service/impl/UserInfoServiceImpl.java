package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.user.personal.PersonalDto;
import com.ws.master_service.dto.customer.user.ProfileDto;
import com.ws.master_service.service.UserInfoService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.BeanUtils;
import com.ws.master_service.utils.common.JsonUtils;
import com.ws.master_service.utils.common.StringUtils;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.validator.AuthValidator;
import com.ws.master_service.utils.validator.user.ProfileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final WsRepository repository;

    @Override
    public Object updateProfile(CurrentUser currentUser, ProfileDto dto) {
        log.info("start updateProfile with payload: {}", JsonUtils.toJson(dto));
        AuthValidator.checkLogin(currentUser);
        var user = repository.userRepository.findByIdAndActive(dto.getId(), true);
        if (null == user) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        if (!currentUser.getId().equals(dto.getId())) {
            throw new WsException(WsCode.FORBIDDEN);
        }
        ProfileValidator.validDto(dto);
        var passwordEncoder = BeanUtils.getBean(PasswordEncoder.class);
        var password = user.getPassword();
        if (!StringUtils.isNullOrEmpty(dto.getNewPassword())) {
            password = passwordEncoder.encode(dto.getNewPassword());
        }
        user.setFirstName(dto.getFirstName().trim())
                .setLastName(dto.getLastName().trim())
                .setPhone(dto.getPhone().trim())
                .setGender(dto.getGender())
                .setDob(dto.getDob())
                .setPassword(password);
        log.info("updateProfile before save: {}", JsonUtils.toJson(user));
        repository.userRepository.save(user);
        log.info("updateProfile after save: {}", JsonUtils.toJson(user));
        return ResData.ok(user.getId());
    }

    @Override
    public Object personal(CurrentUser currentUser) {
        log.info("start personal");
        AuthValidator.checkLogin(currentUser);
        var user = repository.userRepository.findByIdAndActive(currentUser.getId(), true);
        if (null == user) {
            throw new WsException(WsCode.USER_LOCKED);
        }
        return ResData.ok(PersonalDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .email(user.getEmail())
                .gender(user.getGender())
                .phone(user.getPhone())
                .build());
    }
}
