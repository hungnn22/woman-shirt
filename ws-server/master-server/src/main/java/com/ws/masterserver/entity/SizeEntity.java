package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.SizeEnum;
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

    @Enumerated(EnumType.STRING)
    private SizeEnum name;

    private String code;
}