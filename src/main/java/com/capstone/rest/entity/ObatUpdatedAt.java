package com.capstone.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "list_updated_at")
public class ObatUpdatedAt {

    @Id
    @Column(name = "id_updated")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUpdated;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "name_obat", referencedColumnName = "name_obat")
    private Obat obat;

}
