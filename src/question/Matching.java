package question;

import java.util.ArrayList;
import java.util.Collections;

public class Matching extends Question {
	
	private static final String myDelim = "@@@";
	private ArrayList<String> queryList;
	private ArrayList<String> answerList;
	
	private static String type = "matching";
	
	public Matching(ArrayList<String> queryList, ArrayList<String> answerList) {
		this.queryList = queryList;
		this.answerList = answerList;
	}
	
	public Matching(String description, String answer) {
		String[] strs = description.split(myDelim);
		queryList = new ArrayList<String>();
		answerList = new ArrayList<String>();
		if (queryList.size() != answerList.size()) {
			throw new RuntimeException("Query should has equal number of elements with answer");
		}
		queryList = decode(strs[0]);
		answerList = decode(answer);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getDescription() {
		//match1@#match2@#match@#3...@@@shuffledAnswer1@#shuffledAnswer2@#shuffledAnswer3...
		ArrayList<String> shuffledAnswer = (ArrayList<String>) answerList.clone();
		Collections.shuffle(shuffledAnswer);
		return encode(queryList) + myDelim + encode(shuffledAnswer);
	}

	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		return encode(answerList);
	}
	public ArrayList<String> getQueryList(){
		return queryList;
	}
	@Override
	public ArrayList<String> getAnswerList(){
		return answerList;
	}
	

	@Override
	public float calculateScore(Object userResponse) {
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
