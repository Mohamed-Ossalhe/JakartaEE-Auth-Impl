package Application.Controllers;

import Application.DAO.UserDao;
import Application.Entities.User;
import Application.Utils.PasswordHasher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String useremail = req.getParameter("useremail");
        String password = req.getParameter("password");

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        UserDao userDao = new UserDao();
        User user = new User();
        user.setEmail(useremail);
        user.setPassword(password);
        User loggedUser = userDao.findOne(user);
        if (loggedUser != null) {
            if (PasswordHasher.PasswordVerify(user.getPassword(), loggedUser.getPassword())) {
                req.setAttribute("status", "success");
                session.setAttribute("username", loggedUser.getName());
                session.setAttribute("useremail", loggedUser.getEmail());
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }else {
                req.setAttribute("status", "wrong_pwd");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }else {
            req.setAttribute("status", "wrong_creds");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
