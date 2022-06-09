package com.ws.masterserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "type")

public class TypeEntity {
    @Id
    private String id;
}
