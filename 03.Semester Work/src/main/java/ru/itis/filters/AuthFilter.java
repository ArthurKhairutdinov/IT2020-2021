package ru.itis.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        Boolean isAuthenticated = false;
        Boolean sessionExists = session != null;
        Boolean isRequestOnOpenPage = request.getRequestURI().equals("/signIn") ||
                request.getRequestURI().equals("/signUp");
        Boolean isRequestOnHomePage = request.getRequestURI().equals("/index");
        Boolean isRequestOnEmptyPage = request.getRequestURI().equals("/");

        if (sessionExists) {
            isAuthenticated = session.getAttribute("user") != null;
        }

        if (isAuthenticated && !isRequestOnOpenPage || !isAuthenticated && isRequestOnOpenPage || isRequestOnHomePage) {
            filterChain.doFilter(request, response);
        } else if (isAuthenticated && isRequestOnOpenPage) {
            response.sendRedirect("/profile");
        } else if (isRequestOnEmptyPage) {
            response.sendRedirect("/index");
        } else {
            response.sendRedirect("/signIn");
        }
    }

    @Override
    public void destroy() {

    }
}