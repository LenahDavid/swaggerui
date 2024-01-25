package com.example.swaggersui;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findAll();
    // Additional custom queries or methods can be added here
}


