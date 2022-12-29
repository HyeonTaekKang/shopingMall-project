package com.homeshoping.homeshoping.repository.user;

import com.homeshoping.homeshoping.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {
}
