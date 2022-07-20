package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name = "size")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeEntity {

    @Id
    private String id;

    private String name;

    private String code;
}
