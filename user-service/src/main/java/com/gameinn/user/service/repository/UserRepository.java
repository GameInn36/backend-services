package com.gameinn.user.service.repository;

import com.gameinn.user.service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserById(String id);
    List<User> findByUsernameContainingIgnoreCaseOrderByUsername(String username);
}
