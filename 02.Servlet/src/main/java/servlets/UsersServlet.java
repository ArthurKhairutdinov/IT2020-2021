package servlets;

import models.User;
import repositories.UsersRepository;
import repositories.UsersRepositoryJdbcImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private static final String dbUserName = "postgres";
    private static final String dbPassword = "qwerty360";
    private static final String dbAddress = "jdbc:postgresql://localhost:5432/postgres";

    private UsersRepository usersRepository;

    @Override
    public void init() {
        try {
            Connection connection = null;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbAddress,dbUserName,dbPassword);
            usersRepository = new UsersRepositoryJdbcImpl(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ageValue = request.getParameter("age");
        List<User> users = null;
        if (ageValue != null) {
            Integer age = Integer.parseInt(ageValue);
            users = usersRepository.findAllByAge(age);
        } else {
            users = usersRepository.findAll();
        }
        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("WEB-INF/jsp/users.jsp").forward(request, response);
    }
}
