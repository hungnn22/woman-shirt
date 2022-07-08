package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "combo_product_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboProductOptionEntity {
    @Id
    private String id;

    @Column(name = "product_option_id")
    private String productOptionId;

    private String comboId;
}
