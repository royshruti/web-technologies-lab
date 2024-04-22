<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%
    // Initialize variables
    int correctAnswers = 0;
    int totalQuestions = 0;
    
    // Retrieve submitted answers from the request
    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
        String paramName = parameterNames.nextElement();
        if (paramName.startsWith("answer_")) {
            totalQuestions++;
            String questionNo = paramName.substring(7); // Extract question number from parameter name
            String userAnswer = request.getParameter(paramName);
            
            // Retrieve correct answer from the database and compare
            String correctAnswer = ""; // Fetch correct answer from the database based on question number
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/test";
                String user = "guest";
                String password = "guest";
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement("SELECT answer FROM be21131_question25 WHERE no = ?");
                pstmt.setInt(1, Integer.parseInt(questionNo));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    correctAnswer = rs.getString("answer").toString();
                    System.out.println(correctAnswer);
                    System.out.println(userAnswer);

                }
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if (correctAnswer.equals("option"+userAnswer)) {
                correctAnswers++;
            }
        }
    }
%>

<html>
<head>
    <title>Result</title>
</head>
<body>
    <h1>Quiz Result</h1>
    <p>You answered <%= correctAnswers %> out of <%= totalQuestions %> questions correctly.</p>
</body>
</html>
