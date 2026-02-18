package com.eunyeong.care_system.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SeniorRelationsId implements Serializable {
    private String user;   // SeniorRelations의 필드명과 일치해야 함
    private String senior; // SeniorRelations의 필드명과 일치해야 함
}