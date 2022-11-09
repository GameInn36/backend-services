package com.gameinn.user.service.repository;

import com.gameinn.user.service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
