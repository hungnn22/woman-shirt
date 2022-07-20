package com.ws.master_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "location")
public class LocationEntity {


    @Id
    private String id;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "hotline")
    private String hotline;

    @Column(name = "address_link")
    private String addressLink;

}