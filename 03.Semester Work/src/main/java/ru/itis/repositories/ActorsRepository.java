package ru.itis.repositories;

import ru.itis.models.Actor;

import java.util.Optional;

public interface ActorsRepository extends CrudRepository<Actor> {
    public Optional<Actor> findByName(String name);
}
