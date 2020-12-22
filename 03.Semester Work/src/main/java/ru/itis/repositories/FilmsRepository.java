package ru.itis.repositories;

import ru.itis.models.Film;

import java.util.List;

public interface FilmsRepository extends CrudRepository<Film> {
    List<Film> findByName(String name);
    List<Film> findByStarting(String name);
}
