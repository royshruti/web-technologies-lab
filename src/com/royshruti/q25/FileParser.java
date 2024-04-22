package com.royshruti.q25;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.royshruti.q25.Question.Answer;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class FileParser {

    /**
     * Parses the question xml file and generates a list of Questions.
     * 
     * @param xmlFilePath The file path of the questions xml file.
     * @return A list of Questions generated if the questions xml file can be
     *         parsed.
     *         Otherwise returns an empty List.
     */
    public List<Question> parseQuestionsXmlFile(String xmlFilePath) {
        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("question");

            List<Question> qList = new LinkedList<>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Integer no = Integer.parseInt(eElement.getAttributes().getNamedItem("no").getTextContent());
                    String question = eElement.getElementsByTagName("text").item(0).getTextContent();
                    String optionA = eElement.getElementsByTagName("optionA").item(0).getTextContent();
                    String optionB = eElement.getElementsByTagName("optionB").item(0).getTextContent();
                    String optionC = eElement.getElementsByTagName("optionC").item(0).getTextContent();
                    String optionD = eElement.getElementsByTagName("optionD").item(0).getTextContent();
                    String answer = eElement.getElementsByTagName("answer").item(0).getAttributes()
                            .getNamedItem("value")
                            .getTextContent();
                    Question.Answer ans = Answer.valueOf(answer);
                    Question q = new Question(no, question, optionA, optionB, optionC, optionD, ans);

                    qList.add(q);
                }
            }
            return qList;

        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<Question>();
        }
    }

}
