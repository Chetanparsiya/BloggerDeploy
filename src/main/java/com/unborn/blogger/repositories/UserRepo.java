package com.unborn.blogger.repositories;

import com.unborn.blogger.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
