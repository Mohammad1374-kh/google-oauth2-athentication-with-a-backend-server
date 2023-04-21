package com.khosravi.googleoauth2athentication.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class UserDetails extends BaseEntity {

    @Column(length = 50)
    private String name;

    @Column(length = 120)
    private String profileUrl;

    @Column(length = 10)
    private String userLocale;

}
