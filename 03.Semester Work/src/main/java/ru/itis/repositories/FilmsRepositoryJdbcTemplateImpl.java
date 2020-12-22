package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Film;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class FilmsRepositoryJdbcTemplateImpl implements FilmsRepository {

    //language=SQL
    private final static String SQL_FIND_BY_NAME = "select * from films where name=?";
    //language=SQL
    private final static String SQL_INSERT = "insert into films(name,genre,critic_score,users_score,year) values (?,?,?,?,?)";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from films";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from films where id=?";
    //language=SQL
    private final static String SQL_FIND_BY_STARTING = "select * from films where name ILIKE ?";

    private JdbcTemplate jdbcTemplate;

    public FilmsRepositoryJdbcTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Film> filmRowMapper = (row, rowNumber) -> {return Film.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .genre(row.getString("genre"))
            .criticScore(row.getDouble("critic_score"))
            .usersScore(row.getDouble("users_score"))
            .year(row.getInt("year"))
            .build();
    };

    @Override
    public List<Film> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME,filmRowMapper , name);
    }

    @Override
    public List<Film> findByStarting(String name) {
        name = name + "%";
        return jdbcTemplate.query(SQL_FIND_BY_STARTING,filmRowMapper ,name);
    }

    @Override
    public void save(Film entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getName(),
                entity.getGenre(),
                entity.getCriticScore(),
                entity.getUsersScore(),
                entity.getYear());
    }

    @Override
    public void update(Film entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Film> findById(Long id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID,filmRowMapper , id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL,filmRowMapper);
    }
}
