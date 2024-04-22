package com.royshruti.q25;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

//import com.royshruti.q25.Question.Answer;

public class DatabaseConnector {

    // Connection object
    static Connection conn;

    private final static String DB_INSERT = "INSERT INTO be21131_question25 (no, problem, optionA, optionB, optionC, optionD, answer) VALUES (?, ?, ?, ?, ?, ?, ?)";

    static {
        // registering driver and establishing connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "guest";
            String password = "guest";
            conn = DriverManager.getConnection(url, user, password);

            String CREATE_TABLE = """
                    CREATE TABLE IF NOT EXISTS be21131_question25 (
                        no INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        problem TEXT NOT NULL,
                        optionA VARCHAR(255) NOT NULL,
                        optionB VARCHAR(255) NOT NULL,
                        optionC VARCHAR(255) NOT NULL,
                        optionD VARCHAR(255) NOT NULL,
                        answer ENUM('optionA', 'optionB', 'optionC', 'optionD') NOT NULL
                    );
                    """;
            // table creation
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            stmt.close();

        } catch (Exception e) {
            System.out.println("Couldn't connect to database");
            e.printStackTrace();

        }

    }

    void insertQuestions(List<Question> qList) {

        try {
            PreparedStatement ps = conn.prepareStatement(DB_INSERT);
            int totalQuestions = qList.size();

            for (int i = 0; i < totalQuestions; i++) {
                ps.clearParameters();

                Question q = qList.get(i);

                ps.setInt(1, q.getNo());
                ps.setString(2, q.getQuestion());
                ps.setString(3, q.getOptionA());
                ps.setString(4, q.getOptionB());
                ps.setString(5, q.getOptionC());
                ps.setString(6, q.getOptionD());
                ps.setString(7, q.getAnswer().toString());

                ps.addBatch();

            }

            ps.executeBatch();
            ps.close();
        } catch (Exception e) {
            System.out.println("Couldn't insert questions to database");
            e.printStackTrace();

        }

    }

    public void deleteQuestions() {
        try {

            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM be21131_question25");

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
