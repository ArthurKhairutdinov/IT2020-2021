package ru.itis.services;

import ru.itis.models.NamedComment;
import ru.itis.repositories.UsersRepository;
import ru.itis.models.Comment;
import ru.itis.models.User;
import ru.itis.repositories.CommentsRepository;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService{

    UsersRepository usersRepository;
    CommentsRepository commentsRepository;

    public CommentServiceImpl(UsersRepository usersRepository, CommentsRepository commentsRepository){
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public List<NamedComment> merge() {
        List<Comment> comments = commentsRepository.findAll();
        List<User> users = usersRepository.findAll();
        List<NamedComment> namedComments = new ArrayList<>();
        NamedComment namedComment = null;
        for(Comment comment : comments){
            for(User user : users){
                if((user.getId() == comment.getSenderId())){
                    namedComment = new NamedComment(user.getFirstName(), user.getLastName(),comment.getText());
                    namedComments.add(namedComment);
                    break;
                }
            }
            namedComment = null;
        }
        return namedComments;
    }
}
