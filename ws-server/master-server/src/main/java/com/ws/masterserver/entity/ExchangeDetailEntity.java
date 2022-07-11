package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "exchange_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExchangeDetailEntity {
    @Id
    private String id;

    @Column(name = "order_detail_id")
    private String orderDetailId;

    @Column(name = "product_option_id")
    private String productOptionId;

    private Long qty;

    private Long price;
}
