package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.NotificationTypeEnum;
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

    @Column(name = "user_type")
    private String userType;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
}
