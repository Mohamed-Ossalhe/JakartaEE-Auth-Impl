package Application.Controllers;

import Application.DAO.UserDao;
import Application.Entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String mobile = req.getParameter("mobile");

        UserDao userDao = new UserDao();
        User user = new User(name, email, password, mobile);
        User registeredUser = userDao.store(user);
        if (registeredUser != null) {
            req.setAttribute("status", "success");
            req.setAttribute("username", registeredUser.getName());
            req.setAttribute("useremail", registeredUser.getEmail());
        }else {
            req.setAttribute("status", "error");
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
