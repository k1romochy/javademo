package com.example.demo.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryRedis extends CrudRepository<User, Long> {
}
