package com.royshruti.q25;

public class Question {
    enum Answer {
        optionA,
        optionB,
        optionC,
        optionD
    }

    private final int no;
    private String question;
    private String optionA;
    private String optionB;

    @Override
    public String toString() {
        return "Question [no=" + no + ", question=" + question + ", optionA=" + optionA + ", optionB=" + optionB
                + ", optionC=" + optionC + ", optionD=" + optionD + ", answer=" + answer + "]";
    }

    private String optionC;
    private String optionD;
    private Answer answer;

    public Question(int no, String question, String optionA, String optionB, String optionC, String optionD,
            Answer answer) {
        this.no = no;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    public int getNo() {
        return no;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
