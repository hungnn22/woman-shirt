package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "exchange_media")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExchangeMediaEntity {
    @Id
    private String id;

    @Column(name = "exchange_detail_id")
    private String exchangeDetailId;

    private String type;

    private String url;
}
