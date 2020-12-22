package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.UsersFilm;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersFilmRepositoryJdbcTemplateImpl implements UsersFilmRepository {

    //language=SQL
    private final static String SQL_FIND_BY_NAME = "select * from users_films where film_name=?";
    //language=SQL
    private final static String SQL_INSERT = "insert into users_films(users_id,film_name,score) values (?,?,?)";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from users_films";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from users_films where id=?";
    //language=SQL
    private final static String SQL_FIND_BY_USER_ID = "select * from users_films where users_id=?";

    private JdbcTemplate jdbcTemplate;

    public UsersFilmRepositoryJdbcTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<UsersFilm> filmRowMapper = (row, rowNumber) -> {return UsersFilm.builder()
            .id(row.getLong("id"))
            .usersId(row.getLong("users_id"))
            .name(row.getString("film_name"))
            .score(row.getInt("score"))
            .build();
    };

    @Override
    public void save(UsersFilm entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getUsersId(),
                entity.getName(),
                entity.getScore());
    }

    @Override
    public void update(UsersFilm entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<UsersFilm> findById(Long id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID,filmRowMapper , id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<UsersFilm> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL,filmRowMapper);
    }

    @Override
    public List<UsersFilm> findByUserId(Long user_id) {
        return jdbcTemplate.query(SQL_FIND_BY_USER_ID,filmRowMapper , user_id);
    }
}
