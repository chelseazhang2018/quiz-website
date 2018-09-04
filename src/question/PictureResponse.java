package question;

import java.util.ArrayList;

public class PictureResponse extends Question {
	private String imageLocation;
	private ArrayList<String> answerList;
	
	private static String type = "picture-response";
	
	public PictureResponse(String imageLocation, ArrayList<String> answerList) {
		this.imageLocation = imageLocation;
		this.answerList = answerList;
	}
	
	public PictureResponse(String description, String answer) {
		imageLocation = description;
		answerList = decode(answer);
	}

	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:" + type + "\n");
		sb.append("Query:" + imageLocation + "\n");
		sb.append("Answers:\n");
		int i = 1;
		for (String answer:answerList) {
			sb.append("\t" + "(" + i + ") " + answer + "\n");
			i++;
		}
		return sb.toString();
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getDescription() {
		return imageLocation;
	}

	@Override
	public String getAnswer() {
		return encode(answerList);
	}
	
	@Override
	public float calculateScore(Object userResponse) {
		//System.out.println("::::::::::::");
		//System.out.println(userResponse);
		//System.out.println(answerList);
		for (String answer:answerList) {
			if (answer.equals(userResponse)) return 1;
		}
		return 0;
	}
	
	@Override
	public int getTotalQuestions(){
		return 1;
	}
	
}
