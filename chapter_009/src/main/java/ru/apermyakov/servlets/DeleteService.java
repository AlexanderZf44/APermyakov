package ru.apermyakov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Service for initial delete user from db and return json result
 *
 * @author apermyakov
 * @version 1.0
 * @since 15.12.2017
 */
public class DeleteService extends HttpServlet {

    /**
     * Field for user store object.
     */
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransferObject user = new TransferObject();
        user.setId(Integer.valueOf(req.getParameter("id")));
        users.delete(user);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        HttpSession session = req.getSession();
        writer.append("[{\"role\":\"");
        writer.append(String.valueOf(session.getAttribute("role")));
        writer.append("\"}]");
        writer.flush();
    }
}
