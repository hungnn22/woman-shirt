package com.ws.master_service.entity;

import com.ws.master_service.utils.constants.enums.BodyCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "body_weight")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyWeightEntity {
    @Id
    private String id;

    @Column(name = "min_weight")
    private Long minWeight;

    @Column(name = "max_weight")
    private Long maxWeight;

    @Enumerated(EnumType.STRING)
    private BodyCodeEnum code;
}
