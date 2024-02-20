package com.capstone.rest.repository;

import com.capstone.rest.entity.ObatUpdatedAt;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateObatRepository extends JpaRepository<ObatUpdatedAt, Integer> {
}
