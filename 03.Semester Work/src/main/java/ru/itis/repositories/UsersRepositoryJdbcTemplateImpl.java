package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    //language=SQL
    private final static String SQL_FIND_BY_EMAIL = "select * from account where email=?";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from account";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from account where id=?";
    //language=SQL
    private final static String SQL_INSERT = "insert into account(email,first_name,last_name,age,hash_password) values (?,?,?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {return User.builder()
                .age(row.getInt("age"))
                .email(row.getString("email"))
                .firstName(row.getString("first_name"))
                .hashPassword(row.getString("hash_password"))
                .id(row.getLong("id"))
                .lastName(row.getString("last_name"))
                .build();
    };

    @Override
    public Optional<User> findByEmail(String email) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,userRowMapper , email));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_INSERT,
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getHashPassword());

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findById(Long id) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID,userRowMapper , id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL,userRowMapper);
    }
}
