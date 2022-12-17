package com.gameinn.user.service.repository;

import com.gameinn.user.service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findUserByEmailAndPassword(String email, String password);
    User findUserById(String id);
    List<User> findByUsernameStartingWithOrderByUsername(String userName);
}
