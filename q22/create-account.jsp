<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page errorPage="account-not-created.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question 22 - Account Created</title>
</head>
<body>
<%!
    private static final String insertQuery = String.format(
            "INSERT INTO %s (Username, Password) VALUES (?, ?)",
            "be21131_Credentials");
%>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
%>
<%
    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
%>
Neither Username nor Password can be empty
<%
        return;
    }
%>
<%
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "guest";
    String pass = "guest";
    Connection conn = DriverManager.getConnection(url, user, pass);

    PreparedStatement statement = conn.prepareStatement(insertQuery);
    statement.setString(1, username);
    statement.setString(2, password);

    int updates = statement.executeUpdate();
    response.sendRedirect("successful.jsp");
%>
</body>
</html>
