<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to the Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
        }

        h1 {
            color: #333;
            margin-top: 50px;
        }

        p {
            color: #666;
            margin-top: 20px;
        }
    </style>
</head>

<body>
<%
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");

    if (isLoggedIn == null || !isLoggedIn) {
        response.sendRedirect("login.jsp");
    }
%>
<h1>Welcome to your Home Page</h1>
<p>This is a simple welcome message on the home page. Feel free to explore!</p>

<form action="logout.jsp" method="GET">
    <button id="logoutButton">Logout</button>
</form>
</body>

</html>