package question;

import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceWithMultipleAnswer extends Question {
	private String query;
	private ArrayList<String> options;
	private ArrayList<String> answerList;
	
	private static String type = "multiple-choice-with-multiple-answer";
	
	public MultipleChoiceWithMultipleAnswer(String query, ArrayList<String> answerList, ArrayList<String> options) {
		this.query = query;
		this.answerList = answerList;
		this.options = options;
		Collections.sort(answerList);
	}
	
	public MultipleChoiceWithMultipleAnswer(String description, String answer) {
		String[] strs = description.split(delim);
		query = strs[0];
		options = new ArrayList<String> ();
		for (int i = 1; i < strs.length; i++) {
			options.add(strs[i]);
		}
		answerList = decode(answer);
	}
	public ArrayList<String> getOptions(){
		return options;
	}
	public String getQuery(){
		return query;
	}
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:" + type + "\n");
		sb.append("Query:" + query + "\n");
		sb.append("Options:");
		for (int i = 1; i <= options.size(); i++) {
			sb.append(i + "." + options.get(i-1) + " ");
		}
		sb.append("\n");
		sb.append("Answer:");
		for (String ans: answerList) {
			sb.append(ans + ",");
		}
		sb.setCharAt(sb.length()-1, '\n');
		
		return sb.toString();
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getDescription() {
		return query + delim + encode(options);
	}

	@Override
	public String getAnswer() {
		return encode(answerList);
	}

	@Override
	public float calculateScore(Object userResponse) {
		Collections.sort((ArrayList<String>) userResponse);
		return answerList.equals(userResponse)? 1: 0;
	}
	@Override
	public ArrayList<String> getAnswerList() {
		// answer1@#answer2@#answer3
		return  answerList;
	}
	
	@Override
	public int getTotalQuestions(){
		return answerList.size();
	}
}
