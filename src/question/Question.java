package question;

import java.util.ArrayList;

public abstract class Question {
	public abstract String getType();
	public abstract String getDescription();
	public abstract String getAnswer();
	public abstract float calculateScore(Object userResponse);

	public static final String delim = "@#";
	public static final float Score = 10;
	
	public static String encode(ArrayList<String> strs) {
		// give an arrayList of strings, encode into the form str1@#str2@#str3...
		////System.out.println(strs.size());
		StringBuilder sb = new StringBuilder();
		for (String str: strs) {
			sb.append(str+delim);
		}
		////System.out.println("a" + sb.toString() + "a");
		for (int i = 0; i < delim.length() && sb.length() > 0; i++) {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	public static ArrayList<String> decode(String string) {
		ArrayList<String> ret = new ArrayList<String>();
		String[] strs = string.split(delim);
		for (String str: strs) {
			ret.add(str);
		}
		return ret;
	}
	public ArrayList<String> getAnswerList(){
		return null;
	}
	public int getTotalQuestions(){
		return 0;
	}
	
	public String convertToDisplay(String answer) {
		////System.out.println(answer);
		return answer.replaceAll(Question.delim, ", ");
	}
}
