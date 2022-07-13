package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.dto.admin.user.search.UserRes;
import com.ws.masterserver.service.AdminUserSearchService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserSearchServiceImpl implements AdminUserSearchService {

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
        return PageData.setResult(userPage.getContent().stream().map(obj -> UserRes.builder()
                        .id(obj.getId())
                        .name(obj.getFirstName() + " " + obj.getLastName())
                        .email(obj.getEmail())
                        .phone(obj.getPhone())
                        .type(obj.getRole().getName())
                        .active(obj.getActive())
                        .createdDate(DateUtils.toStr(obj.getCreatedDate(), DateUtils.F_DDMMYYYYHHMM))
                        .createdBy(repository.userRepository.findNameById(obj.getCreatedBy()))
                .build()).collect(Collectors.toList()),
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements());

    }
}
