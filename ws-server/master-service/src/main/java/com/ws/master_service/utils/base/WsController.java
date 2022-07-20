package com.ws.master_service.utils.base;

import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.common.BeanUtils;
import com.ws.master_service.utils.common.StringUtils;
import com.ws.master_service.utils.constants.WsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class WsController {
    @Autowired
    protected WsService service;

    protected CurrentUser getCurrentUser() {
        var repository = BeanUtils.getBean(WsRepository.class);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = authentication.getPrincipal().toString().replace("\"", "").trim();
        if (StringUtils.isNullOrEmpty(id)) {
            throw new WsException(WsCode.USER_NOT_FOUND);
        }
        var currentUser = repository.userRepository.findCurrentUserByIdAndActive(id);
        if (currentUser == null) {
            throw new WsException(WsCode.MUST_LOGIN);
        }
        return currentUser;
    }
}
