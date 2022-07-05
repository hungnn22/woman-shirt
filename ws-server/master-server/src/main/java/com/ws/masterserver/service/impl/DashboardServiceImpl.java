package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.dashboard.DashboardDto;
import com.ws.masterserver.dto.admin.dashboard.DayDto;
import com.ws.masterserver.dto.admin.dashboard.EarningDayDto;
import com.ws.masterserver.dto.admin.dashboard.ReportDto;
import com.ws.masterserver.service.DashboardService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.MoneyUtils;
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
    public Object dashboard(CurrentUser currentUser) {
        DashboardDto dashboard = new DashboardDto();
        ReportDto report = new ReportDto();
        List<DayDto> thisWeek = new ArrayList<>();
        List<DayDto> lastWeek = new ArrayList<>();
        var pending = CompletableFuture.runAsync(() -> {
            var pendingList = repository.orderStatusRepository.countPending();
            report.setPending(String.valueOf(pendingList.size()));
        });
        var cancel = CompletableFuture.runAsync(() -> {
            var rejectAndCancelNumberToday = repository.orderStatusRepository.countRejectAndCancelToday();
            report.setCancel(String.valueOf(rejectAndCancelNumberToday));
        });
        var user = CompletableFuture.runAsync(() -> {
            var newUserThisWeek = repository.userRepository.countNewUserThisWeek();
            report.setUser(String.valueOf(newUserThisWeek));
        });
        var today = CompletableFuture.runAsync(() -> {
            var earningToday = repository.orderRepository.getEarningToday();
            report.setToday(MoneyUtils.format(earningToday));
        });
        var week = CompletableFuture.runAsync(() -> {
            var earningThisWeek = repository.orderRepository.getEarningThisWeek();
            report.setWeek(MoneyUtils.format(earningThisWeek));
        });

        var thisWeekFuture = CompletableFuture.runAsync(() -> {
            var earningDayRes = repository.orderRepository.getEarningWeekWithDay(0);
            thisWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
        });
        var lastWeekFuture = CompletableFuture.runAsync(() -> {
            var earningDayRes = repository.orderRepository.getEarningWeekWithDay(1);
            lastWeek.addAll(this.getEarningThisWeekWithDay(earningDayRes));
        });
        try {
            CompletableFuture.allOf(pending, cancel, user, today, week, thisWeekFuture, lastWeekFuture).get();
            dashboard.setReport(report);
            dashboard.setThisWeek(thisWeek);
            dashboard.setLastWeek(lastWeek);
        } catch (Exception e) {
            log.error("dashboard error: {}", e.getMessage());
        }
        return ResData.ok(dashboard);
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
