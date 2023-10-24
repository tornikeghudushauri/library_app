package com.library.app.security.user.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@SequenceGenerator(name = "ID_GEN", sequenceName = "ID_SEQ", initialValue = 1000, allocationSize = 1)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GEN")
    private Long id;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
