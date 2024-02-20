package com.capstone.rest.repository;

import com.capstone.rest.entity.RekamMedis;
import com.capstone.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RmRepository extends JpaRepository<RekamMedis, Long> {

    Optional<RekamMedis> findFirstByUser(User user);

    List<RekamMedis> findAllByUser(User user);

}
