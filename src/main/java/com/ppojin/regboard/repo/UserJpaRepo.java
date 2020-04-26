package com.ppojin.regboard.repo;

import com.ppojin.regboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {
}
