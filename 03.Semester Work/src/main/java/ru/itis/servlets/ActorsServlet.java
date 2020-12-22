package ru.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.models.Actor;
import ru.itis.repositories.ActorsRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/actors")
public class ActorsServlet extends HttpServlet {

    private ActorsRepository actorsRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.actorsRepository = (ActorsRepository) servletContext.getAttribute("actorsContext");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Actor> actors = null;
        actors = actorsRepository.findAll();
        request.setAttribute("actorsForJsp", actors);
        request.getRequestDispatcher("WEB-INF/jsp/actors.jsp").forward(request, response);
    }
}
