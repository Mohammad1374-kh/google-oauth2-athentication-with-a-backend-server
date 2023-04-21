package com.khosravi.googleoauth2athentication.entity;

import com.khosravi.googleoauth2athentication.entity.enums.Role;
import lombok.*;
import lombok.experimental.FieldNameConstants;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.khosravi.googleoauth2athentication.entity.enums.Role.NORMAL;
import static java.time.LocalDateTime.now;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 15)
    @Enumerated(STRING)
    private Role role;

    @Column
    private LocalDateTime roleExpiresAt;

    @Column(length = 100)
    private String password;

    @OneToOne(fetch = LAZY, cascade = ALL)
    private UserDetails userDetails;


    public static User createUserViaGoogleOAuth(String email){
        return User.builder()
                .email(email)
                .role(NORMAL)
                .roleExpiresAt(now().plus(1, ChronoUnit.WEEKS))
                .build();
    }

}
