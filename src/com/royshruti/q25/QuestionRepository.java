package com.royshruti.q25;

import java.util.List;

public class QuestionRepository {

    private DatabaseConnector databaseConnector;

    public QuestionRepository() {
        this.databaseConnector = new DatabaseConnector();
    }

    void insert(String xmlFilePath) {

        FileParser fileParser = new FileParser();
        List<Question> qList = fileParser.parseQuestionsXmlFile(xmlFilePath);
        // for (Question q : qList) {
        // System.out.println(q);
        // }

        databaseConnector.insertQuestions(qList);
    }

    void insert(List<Question> qList) {

        databaseConnector.insertQuestions(qList);
    }

    void deleteAll() {
        databaseConnector.deleteQuestions();
    }

}
