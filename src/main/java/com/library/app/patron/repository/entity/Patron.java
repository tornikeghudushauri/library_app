package com.library.app.patron.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patrons")
@SequenceGenerator(name = "ID_GEN", sequenceName = "ID_SEQ", initialValue = 1000, allocationSize = 1)
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GEN")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "is_member")
    private boolean isMember;

    public boolean isMember() {
        return isMember;
    }
}