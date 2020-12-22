package ru.itis.repositories;

import ru.itis.models.UsersFilm;
import ru.itis.repositories.CrudRepository;

import java.util.List;

public interface UsersFilmRepository extends CrudRepository<UsersFilm> {
    List<UsersFilm> findByUserId(Long user_id);
}
