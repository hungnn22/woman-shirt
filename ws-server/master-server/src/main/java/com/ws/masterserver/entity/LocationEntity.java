package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


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

    @Column(name = "address_details")
    private String addressDetail;

    @Column(name = "hotline")
    private String hotline;

    @Column(name = "address_link")
    private String addressLink;

    @Column(name = "direct_link")
    private String directLink;

}