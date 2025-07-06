package cscorner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String action = request.getParameter("action");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login", "root", "Chetan_2005");

            // SIGNUP
            if ("signup".equals(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                int row = ps.executeUpdate();
                if (row > 0) {
                    response.sendRedirect("login.html");  // after signup, go to login
                } else {
                    response.getWriter().println("<h3>Error: Could not register user</h3>");
                }

            }
            // LOGIN
            else if ("login".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE email=? AND password=?");
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", rs.getString("name"));
                    response.sendRedirect("index.jsp"); // go to home
                } else {
                    response.getWriter().println("<h3>Invalid email or password</h3>");
                }
            }
            // LOGOUT
            else if ("logout".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();
                response.sendRedirect("index.jsp"); // redirect to home
            }

            // INVALID ACTION
            else {
                response.getWriter().println("<h3>Error: Unknown action</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }

    // Handle GET (like logout link click)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
