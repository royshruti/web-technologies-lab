<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Answer Questions</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        p {
            margin-bottom: 10px;
        }
        input[type="radio"] {
            margin-right: 5px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
    
</head>
<body>
    <h1>Answer the following Questions</h1>
    
    <form action="CheckAnswers.jsp" method="post">
    
    <% 
        // Establish connection to the database
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "guest";
            String password = "guest";
            conn = DriverManager.getConnection(url, user, password);
            
            // Retrieve questions and options from the database
            String query = "SELECT no, problem, optionA, optionB, optionC, optionD FROM be21131_question25";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            // Display questions with options
            while (rs.next()) {
                int questionNo = rs.getInt("no");
                String question = rs.getString("problem");
                String optionA = rs.getString("optionA");
                String optionB = rs.getString("optionB");
                String optionC = rs.getString("optionC");
                String optionD = rs.getString("optionD");
    %>
                <p><strong>Question <%= questionNo %>:</strong> <%= question %></p>
                <input type="radio" name="answer_<%= questionNo %>" value="A"> <%= optionA %><br>
                <input type="radio" name="answer_<%= questionNo %>" value="B"> <%= optionB %><br>
                <input type="radio" name="answer_<%= questionNo %>" value="C"> <%= optionC %><br>
                <input type="radio" name="answer_<%= questionNo %>" value="D"> <%= optionD %><br><br>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
    
    <input type="submit" value="Submit Answers">
    
    </form>
</body>
</html>
