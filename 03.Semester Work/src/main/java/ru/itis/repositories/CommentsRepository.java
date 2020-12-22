package ru.itis.repositories;

import ru.itis.models.Comment;

import java.util.List;

public interface CommentsRepository extends CrudRepository<Comment> {
    List<Comment> findBySenderId(Long sender_id);
}
