package com.expensePro.api.EtUserAuthService.repository;

import com.expensePro.api.EtUserAuthService.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {


    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);


}
