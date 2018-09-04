package question;

import java.util.ArrayList;

public class MultipleChoice extends Question {
	private String query;
	private ArrayList<String> options;
	private String answer;
	
	private static String type = "multiple-choice";
	
	public MultipleChoice(String query, String answer, ArrayList<String> options) {
		this.query = query;
		this.answer = answer;
		this.options = options;
	}
	
	public MultipleChoice(String description, String answer) {
		String[] strs = description.split(delim);
		query = strs[0];
		options = new ArrayList<String> ();
		for (int i = 1; i < strs.length; i++) {
			options.add(strs[i]);
		}
		this.answer = answer;
	}
	public String getQuery(){
		return query;
	}
	
	public ArrayList<String> getOptions(){
		return options;
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
		sb.append("Answer:" + answer + "\n");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getDescription() {
		return query+delim+encode(options);
	}

	@Override
	public String getAnswer() {
		return answer;
	}
	
	@Override
	public float calculateScore(Object userResponse) {
		return answer.equals(userResponse)? 1: 0;
	}
	
	@Override
	public int getTotalQuestions(){
		return 1;
	}
}
