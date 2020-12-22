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
import java.util.List;

@WebServlet("/films")
public class FilmsServlet extends HttpServlet {

    private FilmsRepository filmsRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.filmsRepository = (FilmsRepository) servletContext.getAttribute("filmsContext");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Film> films = null;
        films = filmsRepository.findAll();
        request.setAttribute("filmsForJsp", films);
        request.getRequestDispatcher("WEB-INF/jsp/films.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Film film = objectMapper.readValue(request.getReader(),Film.class);
        String films = objectMapper.writeValueAsString(filmsRepository.findByName(film.getName()));
        response.setContentType("application/json");
        response.getWriter().println(films);
    }
}
