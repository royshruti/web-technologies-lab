<%@ page session="true" %>

<%
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
    if (isLoggedIn != null && isLoggedIn) {
        response.sendRedirect("welcome.jsp");
    }
%>
<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top: 50px;
        }

        input[type="text"],
        input[type="password"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>Login</h2>
    <form id="loginForm" method="post">
        <label for="username">Username: </label>
        <input id="username" name="username" placeholder="Username" type="text">
        <label for="password">Password: </label>
        <input id="password" name="password" placeholder="Password" type="password">
        <input type="submit" value="Login">
    </form>
    <div class="error-message" id="errorMessage"></div>
</div>

<script>
    const loginForm = document.getElementById("loginForm");
    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(loginForm);
        const payload = new URLSearchParams(formData).toString();
        const resp = await fetch("/Shruti/question24/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: payload
        })

        const data = await resp.json();
        if (data['isVerified']) {
            // Successful login
            window.location.href = "welcome.jsp";
        } else {
            document.getElementById("errorMessage").innerText = 'authentication details not valid';
        }
    });
</script>

</body>

</html>