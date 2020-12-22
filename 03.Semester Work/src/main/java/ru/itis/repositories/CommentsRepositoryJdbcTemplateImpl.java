package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Comment;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class CommentsRepositoryJdbcTemplateImpl implements CommentsRepository {

    //language=SQL
    private final static String SQL_FIND_BY_SENDER_ID = "select * from comments where sender_id=?";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from comments where id=?";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from comments";
    //language=SQL
    private final static String SQL_INSERT = "insert into comments(sender_id, text) values (?,?)";

    private JdbcTemplate jdbcTemplate;

    public CommentsRepositoryJdbcTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Comment> commentsRowMapper = (row, rowNumber) -> {return Comment.builder()
            .id(row.getLong("id"))
            .senderId(row.getLong("sender_id"))
            .text(row.getString("text"))
            .build();
    };

    @Override
    public Optional<Comment> findById(Long id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID,commentsRowMapper , id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL,commentsRowMapper);
    }

    @Override
    public List<Comment> findBySenderId(Long sender_id) {
        return jdbcTemplate.query(SQL_FIND_BY_SENDER_ID,commentsRowMapper,sender_id);
    }

    @Override
    public void save(Comment entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getSenderId(),
                entity.getText());
    }

    @Override
    public void update(Comment entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
