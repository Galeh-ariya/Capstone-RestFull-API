package com.capstone.rest.repository;

import com.capstone.rest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    long count();

    Optional<User> findFirstByToken(String token);

    List<User> findAllByRole(Integer role);

    Optional<User> findFirstByNoRm(String noRm);

    Integer countAllByCategory(String category);


}
