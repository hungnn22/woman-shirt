package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.dashboard.DashboardDto;
import com.ws.masterserver.service.DashboardService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final WsRepository repository;

    @Override
    public Object dashboard(CurrentUser currentUser) {
        var pending = repository.orderStatusRepository.getPendingNumber().size();
        var orderToday = repository.orderRepository.getNumberToday();
        var cancel = repository.orderStatusRepository.countByStatusIn(List.of(StatusEnum.CANCEL));
        var user = repository.userRepository.countNewUserToday();
        return ResData.ok(DashboardDto.builder()
                        .pending(String.valueOf(pending))
                        .cancel(String.valueOf(cancel))
                        .user(String.valueOf(user))
                .build());
    }
}
