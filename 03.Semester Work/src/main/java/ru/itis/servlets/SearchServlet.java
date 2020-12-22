package ru.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.models.Film;
import ru.itis.repositories.FilmsRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private FilmsRepository filmsRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.filmsRepository = (FilmsRepository) servletContext.getAttribute("filmsContext");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/filmsearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Film film = objectMapper.readValue(request.getReader(), Film.class);
        String films = objectMapper.writeValueAsString(filmsRepository.findByStarting(film.getName()));
        response.setContentType("application/json");
        response.getWriter().println(films);
        System.out.println(film.getName());
    }
}