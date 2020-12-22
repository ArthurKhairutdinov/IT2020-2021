package ru.itis.repositories;

import ru.itis.models.User;
import ru.itis.repositories.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}
