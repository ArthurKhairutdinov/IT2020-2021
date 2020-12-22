package ru.itis.servlets;

import ru.itis.models.NamedComment;
import ru.itis.dto.UserDto;
import ru.itis.models.Comment;
import ru.itis.repositories.CommentsRepositoryJdbcTemplateImpl;
import ru.itis.services.CommentServiceImpl;

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

@WebServlet("/forum")
public class CommentsServlet extends HttpServlet {

    private CommentServiceImpl commentService;
    private CommentsRepositoryJdbcTemplateImpl commentsRepositoryJdbcTemplate;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.commentService = (CommentServiceImpl) servletContext.getAttribute("mergedCommentsContext");
        this.commentsRepositoryJdbcTemplate = (CommentsRepositoryJdbcTemplateImpl) servletContext.getAttribute("commentsContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NamedComment> comments = null;
        comments = commentService.merge();
        req.setAttribute("commentsForJsp", comments);
        req.getRequestDispatcher("WEB-INF/jsp/comments.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("comment_input");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        Long id = user.getId();
        Comment comment = Comment.builder()
                .senderId(id)
                .text(text)
                .build();
        commentsRepositoryJdbcTemplate.save(comment);
        resp.sendRedirect("/forum");
    }
}
