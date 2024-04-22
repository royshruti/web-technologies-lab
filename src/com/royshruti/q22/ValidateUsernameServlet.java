package com.royshruti.q22;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Query as - /question22/accounts/username/validate?username=(username)
 */
@WebServlet("/question22/accounts/username/validate")
public class ValidateUsernameServlet extends HttpServlet {
    private static final String USERNAME_EXISTS_QUERY = String.format("SELECT * FROM %s WHERE Username = ?",
            "be21131_Credentials");

    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // System.out.println(DatabaseConfig.MYSQL_CONNECTOR_URL);
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "guest";
            String pass = "guest";
            this.conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException cnfe) {
            throw new ServletException(cnfe.getMessage());
        } catch (SQLException se) {
            System.out.println(se.getClass().getName());
            throw new ServletException(se.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter();
                PreparedStatement statement = this.conn
                        .prepareStatement(ValidateUsernameServlet.USERNAME_EXISTS_QUERY)) {

            String username = request.getParameter("username");
            if (username == null) {
                System.out.println("username not null from servlet");
                out.println("false");
                return;
            }

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            // if the username exists, it is not valid hence false, else true
            out.println(result.next() ? "false" : "true");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            this.conn.close();
        } catch (SQLException se) {
            System.out.println("could not close database connection");
        }
    }
}
