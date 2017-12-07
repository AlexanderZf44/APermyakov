package ru.apermyakov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to show to user delete interface.
 *
 * @author apermyakov
 * @version 1.0
 * @since 07.12.2017
 */
public class UserDeleteServlet extends HttpServlet {

    /**
     * Field for user store object.
     */
    private final UserStore users = UserStore.getInstance();

    /**
     * Method for work with get request.
     *
     * @param req request
     * @param resp response
     * @throws ServletException servlet e
     * @throws IOException io e
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>");
        writer.append("<html lang=\"en\">");
        writer.append("<head>");
        writer.append("   <meta charset=\"UTF-8\">");
        writer.append("   <title></title>");
        writer.append("</head>");
        writer.append("<body>");
        writer.append("<br/>");
        writer.append("<form action='");
        writer.append(req.getContextPath());
        writer.append("/user/delete' method='post'>");
        writer.append("   Insert ID of user, that need to be delete:");
        writer.append("<br/>");
        writer.append("   ID : <input type='text' name='id'/>");
        writer.append("   <input type='submit' value='Delete user'>");
        writer.append("</form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
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
        users.delete(req);
        resp.sendRedirect(req.getContextPath() + "/interface");
    }
}
