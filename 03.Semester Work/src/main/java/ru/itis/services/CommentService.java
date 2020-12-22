package ru.itis.services;

import ru.itis.models.NamedComment;

import java.util.List;

public interface CommentService {
    List<NamedComment> merge();
}
