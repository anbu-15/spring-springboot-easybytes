package com.eazybytes.jobportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "CONTACTS")
@Getter
@Setter
public class Contact extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "USER_TYPE", nullable = false, length = 50)
    private String userType;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Lob
    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @ColumnDefault("NEW")
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

}
