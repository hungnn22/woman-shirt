package com.ws.master_service.entity;

import com.ws.master_service.utils.constants.enums.NotificationTypeEnum;
import com.ws.master_service.utils.constants.enums.ObjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class NotificationEntity {
    @Id
    private String id;

    private String content;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_read")
    private Boolean isRead;

    private String template;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ObjectTypeEnum objectType;

    @Column(name = "object_type_id")
    private String objectTypeId;
}
