<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Question 19 - Open Account</title>

    <style>
        form {
            display: flex;
            width: 200px;
            flex-direction: column;
            row-gap: 4px;
        }
    </style>
</head>

<body>
<form action="create-account.jsp" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" id="username" minlength="6"/>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" minlength="8"/>
    <input type="submit" value="Create Account"/>
</form>

<div id="username-valid-container"></div>

<script src="script.js"></script>
</body>
</html>
