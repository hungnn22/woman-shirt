package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.dashboard.*;
import com.ws.master_service.service.DashboardService;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.MoneyUtils;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final WsRepository repository;

    @Override
    public Object getReport(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        var report = new ReportDto();
        var pending = CompletableFuture.runAsync(() -> {
            var pendingList = repository.orderStatusRepository.countPending();
            report.setPending((long) pendingList.size());
        });
        var cancel = CompletableFuture.runAsync(() -> {
            var rejectAndCancelNumberToday = repository.orderStatusRepository.countRejectAndCancelToday();
            report.setCancel(rejectAndCancelNumberToday);
        });
        var user = CompletableFuture.runAsync(() -> {
            var newUserThisWeek = repository.userRepository.countNewUserThisWeek();
            report.setUser(newUserThisWeek);
        });
        var today = CompletableFuture.runAsync(() -> {
            var earningToday = repository.orderRepository.getEarningToday();
            report.setToday(MoneyUtils.format(earningToday));
        });
        var week = CompletableFuture.runAsync(() -> {
            var earningThisWeek = repository.orderRepository.getEarningThisWeek();
            report.setWeek(MoneyUtils.format(earningThisWeek));
        });

        try {
            CompletableFuture.allOf(pending, cancel, user, today, week).get();
        } catch (Exception e) {
            log.error("dashboard error: {}", e.getMessage());
        }
        return ResData.ok(report);
    }

    @Override
    public Object getCategoryRevenue(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        return ResData.ok(repository.orderRepository.getCategoryRevenue());
    }

    @Override
    public Object getWeekRevenue(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        var res = new WeekRevenueDto();
        List<DayDto> thisWeek = new ArrayList<>();
        List<DayDto> lastWeek = new ArrayList<>();
        var thisWeekFuture = CompletableFuture.runAsync(() -> {
            var earningDayRes = repository.orderRepository.getEarningWeekWithDay(0);
            thisWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
        });
        var lastWeekFuture = CompletableFuture.runAsync(() -> {
            var earningDayRes = repository.orderRepository.getEarningWeekWithDay(1);
            lastWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
        });
        try {
            CompletableFuture.allOf(thisWeekFuture, lastWeekFuture).get();
            res.setThisWeek(thisWeek);
            res.setLastWeek(lastWeek);
        } catch (Exception e) {
            log.error("getWeekRevenue error: {}", e.getMessage());
        }
        return ResData.ok(res);
    }

    private List<DayDto> getEarningThisWeekWithDay(List<EarningDayDto> list) {
        List<DayDto> res = new ArrayList<>();
        IntStream.rangeClosed(2, 8).forEach(item -> res.add(DayDto.builder()
                .total(0L)
                .totalFmt(MoneyUtils.format(0L))
                .build()));
        if (!list.isEmpty()) {
            for (var obj : list) {
                if (obj.getDayOfWeek() == -1) {
                    obj.setDayOfWeek(7);
                }
                res.set(obj.getDayOfWeek(), DayDto.builder()
                        .total(obj.getTotal())
                        .totalFmt(MoneyUtils.format(obj.getTotal()))
                        .build());
            }
        }
        return res;
    }
}
