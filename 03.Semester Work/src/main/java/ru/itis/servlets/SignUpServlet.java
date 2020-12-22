package ru.itis.servlets;

import ru.itis.dto.UserForm;
import ru.itis.services.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;
    private Validator validator;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        this.signUpService = (SignUpService) servletContext.getAttribute("signUpContext");
        this.validator = (Validator) servletContext.getAttribute("validator");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errors", new ArrayList<>());
        req.getRequestDispatcher("/WEB-INF/jsp/sign_up.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserForm userForm = UserForm.builder()
                .email(req.getParameter("email"))
                .age(Integer.parseInt(req.getParameter("age")))
                .first_name(req.getParameter("firstName"))
                .last_name(req.getParameter("lastName"))
                .password(req.getParameter("password"))
                .build();
        Set<ConstraintViolation<UserForm>> constraintViolations = validator.validate(userForm);
        if (!constraintViolations.isEmpty()) {
            req.setAttribute("errors", constraintViolations);
            req.getRequestDispatcher("/WEB-INF/jsp/sign_up.jsp").forward(req, resp);
        } else {
            signUpService.signUp(userForm);
            resp.sendRedirect("/signUp"); // location=/signUp
        }
    }
}
