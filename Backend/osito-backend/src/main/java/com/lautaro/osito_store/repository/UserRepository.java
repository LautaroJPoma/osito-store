package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lautaro.osito_store.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
