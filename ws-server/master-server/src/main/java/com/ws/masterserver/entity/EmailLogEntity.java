package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailLogEntity {
    @Id
    private String id;
    private String email;
    private String content;
    private Boolean isSent;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
}
