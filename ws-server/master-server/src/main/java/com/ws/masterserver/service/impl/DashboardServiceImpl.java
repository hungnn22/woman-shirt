package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.dashboard.DashboardDto;
import com.ws.masterserver.service.DashboardService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.MoneyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final WsRepository repository;

    @Override
    public Object dashboard(CurrentUser currentUser) {
        DashboardDto dto = new DashboardDto();
        var pending = CompletableFuture.runAsync(() -> {
            var pendingList = repository.orderStatusRepository.countPending();
            dto.setPending(String.valueOf(pendingList.size()));
        });
        var cancel = CompletableFuture.runAsync(() -> {
            var rejectAndCancelNumberToday = repository.orderStatusRepository.countRejectAndCancelToday();
            dto.setCancel(String.valueOf(rejectAndCancelNumberToday));
        });
        var user = CompletableFuture.runAsync(() -> {
            var newUserThisWeek = repository.userRepository.countNewUserThisWeek();
            dto.setUser(String.valueOf(newUserThisWeek));
        });
        var today = CompletableFuture.runAsync(() -> {
            var earningToday = repository.orderRepository.getEarningToday();
            dto.setToday(MoneyUtils.format(earningToday));
        });
        var week = CompletableFuture.runAsync(() -> {
            var earningThisWeek = repository.orderRepository.getEarningThisWeek();
            dto.setWeek(MoneyUtils.format(earningThisWeek));
        });
        try {
            CompletableFuture.allOf(pending, cancel, user, today, week).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResData.ok(dto);
    }
}
