package com.epam.demo.pbl.repository;

import java.util.List;
import java.util.Optional;

import com.epam.demo.pbl.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    List<UserDetails> findByActive(boolean isActive);

    @Query(value = "SELECT count(1) FROM users where mobile_number=:mobileNumber", nativeQuery = true)
    Optional<Long> findCountByMobileNumber(@Param("mobileNumber") long mobileNumber);

    @Query(value = "SELECT * FROM users where mobile_number=:mobileNumber", nativeQuery = true)
    Optional<UserDetails> findByMobileNumber(long mobileNumber);
}
