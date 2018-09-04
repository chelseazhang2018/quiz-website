package support;

import java.util.ArrayList;
import java.util.Date;

import question.Question;

public class Quiz {
	private boolean random;
	private boolean onePage;
	private boolean practiceMode;
	private boolean immediateCorrection;
	private String title;
	private String category;
	private String description;
	private boolean allowPractice;
	private ArrayList<Question> questions;
	private ArrayList<String> tags;
	public static final int getQuestionsForQuiz =5;
	
	public Quiz(boolean random, boolean onePage, boolean practiceMode, boolean immediateCorrection, String title, String category, String description) {
		this(random, onePage, practiceMode, immediateCorrection, title, category, description, false, new ArrayList<Question>(), new ArrayList<String>());
	}
	
	public Quiz(boolean random, boolean onePage, boolean practiceMode, boolean immediateCorrection, String title, String category, String description, boolean allowPractice) {
		this(random, onePage, practiceMode, immediateCorrection, title, category, description, allowPractice, new ArrayList<Question>(), new ArrayList<String>());
	}
	
	public Quiz(boolean random, boolean onePage, boolean practiceMode, boolean immediateCorrection, String title, String category, String description, boolean allowPractice, ArrayList<Question> questions, ArrayList<String> tags) {
		this.random = random;
		this.onePage = onePage;
		this.practiceMode = practiceMode;
		this.immediateCorrection = immediateCorrection;
		this.title = title;
		this.category = category;
		this.description = description;
		this.allowPractice = practiceMode;
		this.questions = questions;
		this.tags = tags;
	}
	
	public void editQuestions (ArrayList<Question> newQuestions){
		questions = newQuestions;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isPracticeAllowed() {
		return allowPractice;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public boolean isRandom() {
		return random;
	}
	
	public boolean isOnePage() {
		return onePage;
	}
	
	public boolean isImmediateCorrection() {
		return immediateCorrection;
	}
	
	public static class QuizAbstract {
		private String title;
		private String category;
		private String description;
		private String createTime;
		private String creator;
		private ArrayList<String> tags;
		private boolean onePage;
		private boolean random;
		private boolean immediateCorrection;
		private boolean allowPractice;
		public QuizAbstract(String title, String category, String description, String createTime, String creator, ArrayList<String> tags, boolean allowPractice, boolean onePage, boolean random, boolean immediateCorrection) {
			this.title = title;
			this.category = category;
			this.description = description;
			this.createTime = createTime;
			this.creator = creator;
			this.tags = tags;
			this.allowPractice = allowPractice;
			this.onePage = onePage;
			this.random = random;
			this.immediateCorrection = immediateCorrection;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Quiz " + title + " abstract:\n");
			sb.append("\tCategory:" + category + ", description:" + description + ", created on:" + createTime + " by:" + creator + " tags:");
			for (String tag: tags) {
				sb.append(tag + ",");
			}
			sb.setCharAt(sb.length()-1, '\n');
			sb.append("Modes: allowPractice:" + allowPractice + ", onepage:" + onePage + ", random:" + random + ", immediateCorrection:" + immediateCorrection + "\n");
			return sb.toString();
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getCategory() {
			return category;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getCreateTime() {
			return createTime;
		}
		
		public String getCreator() {
			return creator;
		}
		
		public void setCreator(String creator) {
			this.creator = creator;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public ArrayList<String> getTags() {
			return tags;
		}
		
		public boolean isRandom() {
			return random;
		}
		
		public boolean isOnePage() {
			return onePage;
		}
		
		public boolean isImmediateCorrection() {
			return immediateCorrection;
		}
		
		public boolean isPracticeAllowed() {
			return allowPractice;
		}
		
		
	}
	
	public static class QuizTakingActivity {
		private String title;
		private String username;
		private float score;
		private String completeTime; //HH:MM:SS
		private String takingTime; //YYYY-MM-DD HH:MM:SS
		
		public QuizTakingActivity(String title, String username, float score, String completeTime, String takingTime) {
			this.title = title;
			this.username = username;
			this.score = score;
			this.completeTime = completeTime;
			this.takingTime = takingTime;
		}
		
		public String getTitle(){
			return title;
		}
		public String getUsername(){
			return username;
		}
		public float getScore(){
			return score;
		}
		public String getCompleteTime(){
			return completeTime;
		}
		public String getTakingTime() {
			return takingTime;
		}
		
		@Override
		public String toString() {
			return "Quiz:" + title + ", Taker:" + username + ", Score:" + score + ", Time Spend:" + completeTime + ", Date:" + takingTime + "\n";
		}
		
		// implement a list of getter methods
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title:");
		sb.append(title);
		sb.append("\n");
		sb.append("Category:");
		sb.append(category);
		sb.append("\n");
		sb.append("Description:");
		sb.append(description);
		sb.append("\n");
		sb.append("Modes:");
		sb.append("random=");
		sb.append(random);
		sb.append(" ");
		sb.append("one-page=");
		sb.append(onePage);
		sb.append(" ");
		sb.append("practice-mode=");
		sb.append(practiceMode);
		sb.append(" ");
		sb.append("immediate-correction=");
		sb.append(immediateCorrection);
		sb.append("\n");
		sb.append("Questions:\n");
		for (int i = 1; i <= questions.size(); i++) {
			sb.append(i + ". " + questions.get(i-1));
		}
		return sb.toString();
	}
}
