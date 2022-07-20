package com.ws.master_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "order_discount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderDiscountEntity {
    @Id
    private String id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "discount_id")
    private String discountId;
}
