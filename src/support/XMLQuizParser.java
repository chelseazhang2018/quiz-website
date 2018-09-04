package support;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import question.*;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLQuizParser {
	public static Quiz parseFile(String filename) {
		Quiz quiz = null;
		try {
			File inputFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			////System.out.println(filename);
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			boolean random = doc.getDocumentElement().getAttribute("random").equals("true");
			boolean onePage = doc.getDocumentElement().getAttribute("one-page").equals("true");
			////System.out.println(doc.getDocumentElement().getAttribute("practice-mode"));
			boolean practiceMode = doc.getDocumentElement().getAttribute("practice-mode").equals("true");
			////System.out.println(practiceMode);
			boolean immediateCorrection = doc.getDocumentElement().getAttribute("immediate-correction").equals("true");
			String title = doc.getElementsByTagName("title").item(0) == null? "": doc.getElementsByTagName("title").item(0).getTextContent();
			String category = doc.getElementsByTagName("category").item(0) == null? "": doc.getElementsByTagName("category").item(0).getTextContent();
			String description = doc.getElementsByTagName("description").item(0) == null? "": doc.getElementsByTagName("description").item(0).getTextContent();
			quiz = new Quiz(random, onePage, practiceMode, immediateCorrection, title, category, description);
			NodeList nList = doc.getElementsByTagName("question");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);		
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element question = (Element) nNode;
					String type = question.getAttribute("type");
					if (type.equals("question-response")) {
						String query = question.getElementsByTagName("query").item(0).getTextContent();
						String answer = question.getElementsByTagName("answer").item(0).getTextContent();
						Question q = new QuestionResponse(query, answer);
						quiz.addQuestion(q);
					} else if (type.equals("fill-in-blank")) {
						String pre = ((Element)question.getElementsByTagName("blank-query").item(0)).getElementsByTagName("pre").item(0).getTextContent();
						String post = ((Element)question.getElementsByTagName("blank-query").item(0)).getElementsByTagName("post").item(0).getTextContent();
						String answer = question.getElementsByTagName("answer").item(0).getTextContent();
						Question q = new FillInBlank(pre, post, answer);
						quiz.addQuestion(q);
					} else if (type.equals("multiple-choice")) {
						String query = question.getElementsByTagName("query").item(0).getTextContent();
						NodeList nodeList = question.getElementsByTagName("option");
						String answer = "";
						ArrayList<String> options = new ArrayList<String>();
						for (int j = 0; j < nodeList.getLength(); j++) {
							Node option = nodeList.item(j);
							if (option.hasAttributes()) {
								answer = option.getTextContent();
							}
							options.add(option.getTextContent());
						}
						Question q = new MultipleChoice(query, answer, options);
						quiz.addQuestion(q);
					} else if (type.equals("picture-response")) {
						String imageLocation = question.getElementsByTagName("image-location").item(0).getTextContent();
						////System.out.println(imageLocation);
						ArrayList<String> answerList = new ArrayList<String>();
						if (question.getElementsByTagName("answer-list").item(0) == null) { // single answer
							answerList.add(question.getElementsByTagName("answer").item(0).getTextContent());
						} else {
							NodeList nodeList = ((Element)question.getElementsByTagName("answer-list").item(0)).getElementsByTagName("answer");
							for (int j = 0; j < nodeList.getLength(); j++) {
								Node answer = nodeList.item(j);
								answerList.add(answer.getTextContent());
							}
						}
						Question q = new PictureResponse(imageLocation, answerList);
						quiz.addQuestion(q);
					} else if (type.equals("matching")) {
						ArrayList<String> queryList = new ArrayList<String>();
						ArrayList<String> answerList = new ArrayList<String>();
						NodeList nodeList = ((Element)question.getElementsByTagName("query-list").item(0)).getElementsByTagName("query");
						for (int j = 0; j < nodeList.getLength(); j++) {
							Node query = nodeList.item(j);
							queryList.add(query.getTextContent());
						}
						nodeList = ((Element)question.getElementsByTagName("answer-list").item(0)).getElementsByTagName("answer");
						for (int j = 0; j < nodeList.getLength(); j++) {
							Node answer = nodeList.item(j);
							answerList.add(answer.getTextContent());
						}
						Question q = new Matching(queryList, answerList);
						quiz.addQuestion(q);
					} else if (type.equals("multi-answer")) {
						String query = question.getElementsByTagName("query").item(0).getTextContent();
						ArrayList<String> answerList = new ArrayList<String>();
						NodeList nodeList = ((Element)question.getElementsByTagName("answer-list").item(0)).getElementsByTagName("answer");
						for (int j = 0; j < nodeList.getLength(); j++) {
							Node answer = nodeList.item(j);
							answerList.add(answer.getTextContent());
						}
						boolean hasOrder = question.getAttribute("has-order").equals("true");
						Question q = new MultiAnswer(query, answerList, hasOrder);
						quiz.addQuestion(q);	
					} else if (type.equals("multiple-choice-with-multiple-answer")) {
						String query = question.getElementsByTagName("query").item(0).getTextContent();
						NodeList nodeList = question.getElementsByTagName("option");
						ArrayList<String> answer = new ArrayList<String>();
						ArrayList<String> options = new ArrayList<String>();
						for (int j = 0; j < nodeList.getLength(); j++) {
							Node option = nodeList.item(j);
							if (option.hasAttributes()) {
								answer.add(option.getTextContent());
							}
							options.add(option.getTextContent());
						}
						Question q = new MultipleChoiceWithMultipleAnswer(query, answer, options);
						quiz.addQuestion(q);						
					} else {
						//System.out.println(type);
						throw new RuntimeException("Un-supported question type");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quiz;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Quiz quiz = XMLQuizParser.parseFile("Quizzes/cities.xml");
		//System.out.println(quiz);
	}
}
