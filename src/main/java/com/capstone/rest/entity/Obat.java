package com.capstone.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "obat")
public class Obat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obat")
    private Integer idObat;

    @Column(name = "name_obat")
    private String nameObat;

    @Column(name = "expired")
    private LocalDate expiredAt;

    @Column(name = "min_stock")
    private Integer mintStock;

    private Integer stock;

    @Column(name = "used_total", columnDefinition = "INT DEFAULT 0")
    private Integer usedTotal;

    @OneToMany(mappedBy = "obat")
    private List<ObatUpdatedAt> obatUpdatedAts;

}
