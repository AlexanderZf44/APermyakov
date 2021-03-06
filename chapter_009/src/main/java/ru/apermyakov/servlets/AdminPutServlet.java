package ru.apermyakov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to show to user put interface.
 *
 * @author apermyakov
 * @version 1.0
 * @since 07.12.2017
 */
public class AdminPutServlet extends HttpServlet {

    /**
     * Field for user store object.
     */
    private final UserStore users = UserStore.getInstance();

    /**
     * Method for work with get request.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", users.getRoles());
        req.getRequestDispatcher("/WEB-INF/views/AdminPutUser.jsp").forward(req, resp);
    }

    /**
     * Method for work with post request.
     *
     * @param req request
     * @param resp response
     * @throws ServletException servlet e
     * @throws IOException io e
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        users.put(req);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
