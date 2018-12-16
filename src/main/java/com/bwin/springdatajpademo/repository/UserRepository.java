package com.bwin.springdatajpademo.repository;

import com.bwin.springdatajpademo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
