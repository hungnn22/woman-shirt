package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.notification.NotificationDto;
import com.ws.master_service.dto.admin.notification.NotificationRes;
import com.ws.master_service.entity.UserNotificationEntity;
import com.ws.master_service.service.NotificationService;
import com.ws.master_service.service.WebSocketService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.DateUtils;
import com.ws.master_service.utils.common.JsonUtils;
import com.ws.master_service.utils.common.UidUtils;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final WsRepository repository;
    private final WebSocketService wsService;

    /**
     * @param currentUser
     * @return
     */
    @Override
    public Object get4Admin(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        var notifications = getNotificationDtos(currentUser, 3);
        return ResData.ok(NotificationRes.builder()
                .notifications(notifications)
                .unreadNumber(repository.userNotificationRepository.countUnreadNumber(currentUser.getId()))
                .build());
    }

    @NotNull
    private List<NotificationDto> getNotificationDtos(CurrentUser currentUser, Integer pageSize) {
        List<NotificationDto> notifications = new ArrayList<>();
        var notificationEntities = repository.notificationRepository.find4Elements4Admin(PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdDate")));
        if (!notificationEntities.isEmpty()) {
            notifications = notificationEntities.stream().map(obj -> NotificationDto.builder()
                    .id(obj.getId())
                    .content(obj.getContent())
                    .createdDate(DateUtils.toStr(obj.getCreatedDate(), DateUtils.DATE_TIME_FORMAT_VI))
                    .div(obj.getType().getDiv())
                    .icon(obj.getType().getIcon())
                    .isRead(repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), obj.getId()))
                    .objectType(obj.getObjectType())
                    .objectTypeId(obj.getObjectTypeId())
                    .build()).collect(Collectors.toList());
        }
        return notifications;
    }

    @Override
    @Transactional
    public Long readTop3Notification4Admin(CurrentUser currentUser, List<String> dto) {
        log.info("NotificationServiceImpl read4admin start");
        if (!dto.isEmpty()) {
            saveUserNotifications(currentUser, dto);
        }
        log.info("NotificationServiceImpl read4admin finish");
        return repository.userNotificationRepository.countUnreadNumber(currentUser.getId());
    }

    private void saveUserNotifications(CurrentUser currentUser, List<String> dto) {
        dto.forEach(obj -> {
            if (!repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), obj)) {
                repository.userNotificationRepository.save(UserNotificationEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .notificationId(obj)
                        .build());
            }
        });
    }

    @Override
    public Object search4Admin(CurrentUser currentUser, Integer pageSize) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_ADMIN);
        return getNotificationDtos(currentUser, pageSize);
    }

    @Override
    @Transactional
    public Long readAll4Admin(CurrentUser currentUser) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_ADMIN);
        var notification4Admin = repository.notificationRepository.find4Admin();
        if (!notification4Admin.isEmpty()) {
            saveUserNotifications(currentUser, notification4Admin);
        }
        return -1L;
    }

    @Override
    @Transactional
    public void readById4Admin(CurrentUser currentUser, String id) {
        log.info("NotificationServiceImpl readById4Admin start with payload: {}", id);
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        if (!repository.notificationRepository.existsById(id)) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        if (!repository.userNotificationRepository.existsByUserIdAndNotificationId(currentUser.getId(), id)) {
            var userNotification = UserNotificationEntity.builder()
                    .id(UidUtils.generateUid())
                    .userId(currentUser.getId())
                    .notificationId(id)
                    .build();
            repository.userNotificationRepository.save(userNotification);
            log.info("NotificationServiceImpl readById4Admin save userNotificationEntity: {}", JsonUtils.toJson(userNotification));

            wsService.changeUnreadNumberNotification4Admin();
        }
    }
}
