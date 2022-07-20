package com.ws.master_service.entity;

import com.ws.master_service.utils.constants.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name = "review_media")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReviewMediaEntity {
    @Id
    private String id;

    private String url;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Column(name = "review_id")
    private String reviewId;
}
