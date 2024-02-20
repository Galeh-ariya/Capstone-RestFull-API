package com.capstone.rest.repository;

import com.capstone.rest.entity.Obat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObatRepository extends JpaRepository<Obat, Integer> {

    Optional<Obat> findFirstByIdObat(Integer idBbat);

    Optional<Obat> findFirstByNameObat(String obat);

}
