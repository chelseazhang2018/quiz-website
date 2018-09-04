package question;

public class FillInBlank extends Question {
	private String pre;
	private String post;
	private String answer;
	
	private static String type = "fill-in-blank";
			
	public FillInBlank(String pre, String post, String answer) {
		this.pre = pre;
		this.post = post;
		this.answer = answer;
	}
	
	public FillInBlank(String description, String answer) {
		String[] strs = description.split(delim);
		pre = strs[0];
		post = strs[1];
		this.answer = answer;
	}


	@Override
	public String getDescription() {
		return pre + delim + post;
	}
	
	@Override
	public String getAnswer() {
		return answer;
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type:" + type + "\n");
		sb.append("Query:" + pre + " ____ " + post + "\n");
		sb.append("Answer:" + answer + "\n");
		return sb.toString();
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public String getPre(){
		return pre;
	}
	public String getPost(){
		return post;
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
