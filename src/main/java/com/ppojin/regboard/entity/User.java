package com.ppojin.regboard.entity;

import lombok.*;

import javax.persistence.*;

@Builder @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userFk;
    @Column(nullable = false, unique = true, length = 30)
    private String uid;
    @Column(nullable = false, length =100)
    private String name;
}
