package com.capstone.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;

    private String password;

    private String name;

    private Integer role;

    @Column(name = "no_hp")
    private String noHp;

    private String gender;

    private String birthplace;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String category;

    private String instansi;

    @Column(name = "no_rm")
    private String noRm;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<RekamMedis> rekamMedis;

}
