package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import repositories.RowMapper;
import repositories.SimpleJdbcTemplate;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@WebServlet("/users/search")
public class AjaxServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UsersRepository usersRepository;

    private static final String dbUserName = "postgres";
    private static final String dbPassword = "qwerty360";
    private static final String dbAddress = "jdbc:postgresql://localhost:5432/postgres";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/ajax.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = objectMapper.readValue(request.getReader(), User.class);
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbAddress,dbUserName,dbPassword);
            usersRepository = new UsersRepositoryJdbcImpl(connection);
            String users = objectMapper.writeValueAsString(usersRepository.findAllByNameStartingWith(user.getName()));
            response.setContentType("application/json");
            response.getWriter().println(users);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
