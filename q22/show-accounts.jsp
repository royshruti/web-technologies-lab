<%@page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question 22 - Accounts</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
    <style>
        * {
            font-family: "Poppins", sans-serif;
        }
    </style>
</head>
<body>
<%!
    private final String retrieveQuery = String.format("SELECT * FROM %s", "be21131_Credentials");
%>
<%
    try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "guest";
        String pass = "guest";
        Connection conn = DriverManager.getConnection(url, user, pass);
        PreparedStatement statement = conn.prepareStatement(retrieveQuery);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String username = rs.getString("Username");
            String password = rs.getString("Password");

            out.println("<div>");
            out.println(String.format("<span> be21131_Credentials : %s\t\t%s </span>", username, password));
            out.println("</div>");
        }
    } catch (Exception e) {
        out.println(e.getMessage());
    }
%>
</body>
</html>
