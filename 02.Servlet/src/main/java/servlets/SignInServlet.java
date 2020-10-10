package servlets;

import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private static final String dbUserName = "postgres";
    private static final String dbPassword = "qwerty360";
    private static final String dbAddress = "jdbc:postgresql://localhost:5432/postgres";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/html/signin.html").forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbAddress,dbUserName,dbPassword);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        String login = request.getParameter("login_input");
        String password = request.getParameter("password_input");

        String sqlSearch = "select count(*) from signin where login='"+login +"' and password='"+password+"';";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSearch);
        resultSet.next();
        int result = resultSet.getInt(1);

        if(result == 1){
            UUID uuid = UUID.randomUUID();
            Cookie cookie = new Cookie("cookie", uuid.toString());
            response.addCookie(cookie);
            String sqlCookie = "update signin set cookie='"+uuid.toString()+"';";
            System.out.println(sqlCookie);
            Statement statement1 = connection.createStatement();
            statement1.execute(sqlCookie);
            response.sendRedirect("/profile");
        }else {
            response.sendRedirect("/reg");
        }


    }
}
