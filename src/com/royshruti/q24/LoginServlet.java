package com.royshruti.q24;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/question24/login")
public class LoginServlet extends HttpServlet {
    private static final String validString = "{\n" +
            "    \"isVerified\": true\n" +
            "}";

    private static final String notValidString = "{\n" +
            "    \"isVerified\": false\n" +
            "}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("login servlet hit");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DatabaseConnector databaseConnector = new DatabaseConnector();
        String dbPassword = databaseConnector.getCredentials(username);
        if (dbPassword.isEmpty())
            System.out.println("No password for username exists");

        HttpSession session = request.getSession(true);

        // Check if username and password match expected values
        if (!dbPassword.isEmpty() && dbPassword.equals(password)) {
            // Login successful
            session.setAttribute("isLoggedIn", true);

            // response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(validString);
        } else {
            session.setAttribute("isLoggedIn", false);

            // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(notValidString);
        }
    }
}
