package com.lql.demo.repository;

import com.lql.demo.auth.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Cacheable("users")
    Optional<User> findByUsername(String username);


    List<User> findAll();
}
