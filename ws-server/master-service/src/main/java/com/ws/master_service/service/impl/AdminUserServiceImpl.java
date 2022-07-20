package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.user.search.UserReq;
import com.ws.master_service.dto.admin.user.search.UserRes;
import com.ws.master_service.entity.UserEntity;
import com.ws.master_service.service.AdminUserService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.DateUtils;
import com.ws.master_service.utils.common.PageableUtils;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, UserReq req) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        RoleEnum diffRole;
        if (currentUser.getRole().equals(RoleEnum.ROLE_ADMIN)) {
            diffRole = null;
        } else {
            diffRole = RoleEnum.ROLE_ADMIN;
        }
        var pageable = PageableUtils.getPageable(req.getPageReq());
        var userPage = repository.userRepository.search(req.getTextSearch(), req.getActive(), req.getRole(), diffRole, pageable);
        if (userPage.isEmpty()) {
            return PageData.setEmpty(req.getPageReq());
        }
        return PageData.setResult(userPage.getContent().stream().map(obj -> this.getUserResFromUserEntity(obj)).collect(Collectors.toList()),
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements());

    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        var user = repository.userRepository.findById(id).orElseThrow(() -> {
            throw new WsException(WsCode.USER_NOT_FOUND);
        });

        return ResData.ok(this.getUserResFromUserEntity(user));
    }


    private UserRes getUserResFromUserEntity(UserEntity user) {
        return UserRes.builder()
                .id(user.getId())
                .combinationName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roleValue(user.getRole().getName())
                .active(user.getActive())
                .createdDateValue(DateUtils.toStr(user.getCreatedDate(), DateUtils.F_DDMMYYYYHHMM))
                .createdByValue(repository.userRepository.findNameById(user.getCreatedBy()))
                .avatar(user.getAvatar())
                .build();
    }

}
