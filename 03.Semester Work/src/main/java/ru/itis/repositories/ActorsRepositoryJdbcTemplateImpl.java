package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Actor;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ActorsRepositoryJdbcTemplateImpl implements ActorsRepository {

    //language=SQL
    private final static String SQL_FIND_BY_NAME = "select * from actors where first_name=?";
    //language=SQL
    private final static String SQL_INSERT = "insert into actors(first_name, last_name, age) VALUES (?,?,?);";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from actors";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from films where id=?";

    private JdbcTemplate jdbcTemplate;

    public ActorsRepositoryJdbcTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private RowMapper<Actor> actorRowMapper = (row, rowNumber) -> {return Actor.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();
    };

    @Override
    public Optional<Actor> findByName(String name) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_NAME,actorRowMapper , name));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void save(Actor entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge());
    }

    @Override
    public void update(Actor entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Actor> findById(Long id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID,actorRowMapper , id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Actor> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL,actorRowMapper);
    }
}
