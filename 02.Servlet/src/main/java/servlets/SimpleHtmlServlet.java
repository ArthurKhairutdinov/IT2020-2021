package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/reg")
public class SimpleHtmlServlet extends HttpServlet {
    private static final String dbUserName = "postgres";
    private static final String dbPassword = "qwerty360";
    private static final String dbAddress = "jdbc:postgresql://localhost:5432/postgres";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/html/reg.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("Success");

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbAddress,dbUserName,dbPassword);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        String name = request.getParameter("name_input");
        String surname = request.getParameter("surname_input");
        String age = request.getParameter("age_input");
        Integer agel = Integer.parseInt(age);

        String sqlInsertUser = "insert into reg(name, surname, age) " +
                "values (?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlInsertUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, agel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int affectedRows = 0;
        try {
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(affectedRows);
    }
}
