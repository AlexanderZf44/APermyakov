package ru.apermyakov.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

public class JsonSignInService extends HttpServlet {

    /**
     * Field for user store object.
     */
    private final UserStore users = UserStore.getInstance();

    /**
     * Field for enable sessions.
     */
    private final Sessions sessions = Sessions.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String logPas = req.getHeader("Authorization");
        String res = new String(Base64.getDecoder().decode(logPas.replace("Basic ", "")));
        String[] authPare = res.split(":");
        TransferObject user = new TransferObject();
        user.setLogin(authPare[0]);
        user.setPassword(authPare[1]);

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("[{\"access\":\"");
        if (users.isPastInspection(user)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", user.getLogin());
            session.setAttribute("role", users.getRole(user));
            for (Cookie cookie : req.getCookies()) {
                if (("JSESSIONID").equals(cookie.getName())) {
                    this.sessions.putSession(cookie.getValue());
                    writer.append("allow\"}]");
                    break;
                }
            }
        } else {
            writer.append("deny\"}]");
        }
        writer.flush();
    }
}
