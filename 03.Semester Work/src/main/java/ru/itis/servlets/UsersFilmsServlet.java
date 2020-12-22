package ru.itis.servlets;

import ru.itis.dto.UserDto;
import ru.itis.models.UsersFilm;
import ru.itis.repositories.UsersFilmRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myfilms")
public class UsersFilmsServlet extends HttpServlet {

    private UsersFilmRepository usersFilmRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersFilmRepository = (UsersFilmRepository) servletContext.getAttribute("usersFilmsContext");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute("user");
        Long id = userDto.getId();
        List<UsersFilm> films = null;
        films = usersFilmRepository.findByUserId(id);
        request.setAttribute("userfilmsForJsp", films);
        request.getRequestDispatcher("WEB-INF/jsp/myfilms.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("name_input");
        Integer score = Integer.parseInt(request.getParameter("score"));
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        Long id = user.getId();
        UsersFilm usersFilm = UsersFilm.builder()
                .usersId(id)
                .name(text)
                .score(score)
                .build();
        usersFilmRepository.save(usersFilm);
        response.sendRedirect("/myfilms");
    }
}
