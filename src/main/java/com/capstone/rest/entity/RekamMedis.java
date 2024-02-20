package com.capstone.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rekam_medis")
public class RekamMedis {

    @Id
    @Column(name = "id_rm")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObat;

    private String anamnesa;

    private String diagnosis;

    private String therapy;

    @Column(name = "therapy_2")
    private String therapy2;

    @Column(name = "therapy_3")
    private String therapy3;

    @Column(name = "therapy_4")
    private String therapy4;

    @Column(name = "total_obat")
    private Integer totalObat;

    @Column(name = "total_obat_2")
    private Integer totalObat2;

    @Column(name = "total_obat_3")
    private Integer totalObat3;

    @Column(name = "total_obat_4")
    private Integer totalObat4;

    private String keterangan;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "no_rm", referencedColumnName = "no_rm")
    private User user;

}
