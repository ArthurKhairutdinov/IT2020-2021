package ru.itis.listener;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.repositories.*;
import ru.itis.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("qwerty360");
        dataSource.setUsername("postgres");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(usersRepository, passwordEncoder);
        servletContext.setAttribute("signUpContext",signUpService);

        FilmsRepository filmsRepository = new FilmsRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("filmsContext",filmsRepository);

        SignInService signInService = new SignInServiceImpl(usersRepository, passwordEncoder);
        servletContext.setAttribute("signInContext",signInService);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        servletContext.setAttribute("validator", validator);

        CommentsRepository commentsRepository = new CommentsRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("commentsContext",commentsRepository);

        CommentServiceImpl commentService = new CommentServiceImpl(usersRepository,commentsRepository);
        servletContext.setAttribute("mergedCommentsContext",commentService);

        UsersFilmRepository usersFilmRepository = new UsersFilmRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("usersFilmsContext",usersFilmRepository);

        ActorsRepository actorsRepository = new ActorsRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("actorsContext",actorsRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
