package question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiAnswer extends Question {
	
	private String query;
	private ArrayList<String> answerList;
	private boolean hasOrder;
	
	private static String type = "multi-answer";
	
	public MultiAnswer(String query, ArrayList<String> answerList, boolean hasOrder) {
		this.query = query;
		this.answerList = answerList;
		this.hasOrder = hasOrder;
		if (!hasOrder) {
			Collections.sort(answerList);
		}
	}
	
	public MultiAnswer(String description, String answer) {
		String[] strs = description.split(delim);
		query = strs[0];
		hasOrder = strs[1].equals("true");
		answerList = decode(answer);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getDescription() {
		// in the form of query + @# + hasOrder infomation
		return query + Question.delim + hasOrder;
	}

	@Override
	public String getAnswer() {
		// answer1@#answer2@#answer3
		return encode(answerList);
	}
	@Override
	public ArrayList<String> getAnswerList() {
		// answer1@#answer2@#answer3
		return  answerList;
	}
	
	public String getQuery(){
		return query;
	}
	public int getNumOfAnswers(){
		return answerList.size();
	}

	@Override
	public float calculateScore(Object userResponse) {
		if (hasOrder) {
			
		} else {
			Collections.sort((ArrayList<String>) userResponse);
			
		}
		float score = 0;
		ArrayList<String> userAnswers = (ArrayList<String>)userResponse;
		for(int i = 0;i<userAnswers.size();i++){
			if(userAnswers.get(i).equals(answerList.get(i))){
				score+=1;
			}
		}
		// TODO Auto-generated method stub
		return score;
	}
	
	@Override
	public int getTotalQuestions(){
		return answerList.size();
	}
	
}
