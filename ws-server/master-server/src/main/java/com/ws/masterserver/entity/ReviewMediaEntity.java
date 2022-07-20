package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

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
