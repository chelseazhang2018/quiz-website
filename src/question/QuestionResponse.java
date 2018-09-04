package question;

public class QuestionResponse extends Question {
	String query;
	String answer;
	
	private static String type = "question-response";

	public QuestionResponse(String query, String answer) {
		this.query = query;
		this.answer = answer;
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:" + type + "\n");
		sb.append("Query:" + query + "\n");
		sb.append("Answer:" + answer + "\n");
		return sb.toString();
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getDescription() {
		return query;
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
