package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.notification.NotificationDto;
import com.ws.masterserver.dto.admin.notification.NotificationRes;
import com.ws.masterserver.entity.NotificationEntity;
import com.ws.masterserver.service.NotificationService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.NotificationUtils;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validate.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final WsRepository repository;

    /**
     * @param currentUser
     * @return
     */
    @Override
    public Object get4Admin(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        List<NotificationDto> notifications = new ArrayList<>();
        AtomicReference<Integer> unreadNumber = new AtomicReference<>(0);
        List<NotificationEntity> notificationEntities = repository.notificationRepository.find4Elements4Admin(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate")));
        if (!notificationEntities.isEmpty()) {
            notifications = notificationEntities.stream().map(obj -> {
                var isRead = repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), obj.getId());
                if (!isRead) {
                    unreadNumber.updateAndGet(v -> v + 1);
                }
                return NotificationDto.builder()
                        .id(obj.getId())
                        .content(obj.getContent())
                        .createdDate(NotificationUtils.getCreatedDate(obj.getCreatedDate()))
                        .div(obj.getType().getDiv())
                        .icon(obj.getType().getIcon())
                        .isRead(isRead)
                        .build();
            }).collect(Collectors.toList());
        }
        return ResData.ok(NotificationRes.builder()
                .notifications(notifications)
                .unreadNumber(unreadNumber.get())
                .build());
    }
}
