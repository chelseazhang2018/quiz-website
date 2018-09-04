package database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import support.*;
import question.*;

public class DBInterface {
	private DBConnection connector;
	private static final String quizTable = "Quizzes";
	private static final String userTable = "Users";
	private static final String questionTable = "Questions";
	private static final String historyTable = "Histories";
	private static final String relationTable = "Relationships";
	private static final String messageTable = "Messages";
	private static final String announceTable = "Announcements";
	private static final String reviewTable = "Reviews";
	private static final String banTable = "Bans";
	private static final String securityTable = "Securities";
	
	private static class Pair {
		public String first;
		public String second;
		
		public Pair(String first, String second) {
			this.first = first;
			this.second = second;
		}
	}
	
	private void createTable(String tableName, ArrayList<Pair> attributes) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + tableName + "(");
		for (Pair attribute: attributes) {
			sb.append(attribute.first + " " + attribute.second + ",");
		}
		sb.deleteCharAt(sb.length()-1); // last ','
		sb.append(");");
		connector.execute(sb.toString());
	}
	
	public void createQuizTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("title", "TEXT"));
		attributes.add(new Pair("category", "TEXT"));
		attributes.add(new Pair("description", "TEXT"));
		attributes.add(new Pair("allowPractice", "TEXT"));
		attributes.add(new Pair("questions", "TEXT"));
		attributes.add(new Pair("creator", "TEXT"));
		attributes.add(new Pair("createTime", "TIMESTAMP"));
		attributes.add(new Pair("tags", "TEXT"));
		attributes.add(new Pair("onePage", "TEXT"));
		attributes.add(new Pair("random", "TEXT"));
		attributes.add(new Pair("immediateCorrection", "TEXT"));
		createTable(quizTable, attributes);
	}
	
	public void createUserTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("uid", "TEXT"));
		attributes.add(new Pair("password", "TEXT"));
		attributes.add(new Pair("achievements", "TEXT"));
		attributes.add(new Pair("priority", "TEXT"));
		createTable(userTable, attributes);	
	}
	
	public void createQuestionTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("qid", "BIGINT"));
		attributes.add(new Pair("type", "TEXT"));
		attributes.add(new Pair("description", "TEXT"));
		attributes.add(new Pair("answer", "TEXT"));
		createTable(questionTable, attributes);	
	}
	
	public void createHistoryTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("uid", "TEXT"));
		attributes.add(new Pair("title", "TEXT"));
		attributes.add(new Pair("score", "FLOAT"));
		attributes.add(new Pair("completeTime", "TIME")); // duration for taking a quiz
		attributes.add(new Pair("recordTime", "TIMESTAMP"));
		createTable(historyTable, attributes);
	}
	
	public void createRelationTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("uid1", "TEXT"));
		attributes.add(new Pair("uid2", "TEXT"));
		createTable(relationTable, attributes);
	}
	
	public void createMessageTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("toUid", "TEXT"));
		attributes.add(new Pair("fromUid", "TEXT"));
		attributes.add(new Pair("message", "TEXT"));
		attributes.add(new Pair("status", "TEXT"));
		attributes.add(new Pair("sendTime", "TIMESTAMP"));
		createTable(messageTable, attributes);
	}
	
	public void createAnnouncementTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("announcement", "TEXT"));
		attributes.add(new Pair("administrator", "TEXT"));
		attributes.add(new Pair("announceTime", "TIMESTAMP"));
		createTable(announceTable, attributes);
	}
	
	public void createReviewTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("title", "TEXT"));
		attributes.add(new Pair("reviewer", "TEXT"));
		attributes.add(new Pair("review", "TEXT"));
		attributes.add(new Pair("reviewTime", "TIMESTAMP"));
		createTable(reviewTable, attributes);
	}
	
	public void createBanTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("uid", "TEXT"));
		attributes.add(new Pair("bannedUntil", "TIMESTAMP"));
		createTable(banTable, attributes);
	}
	
	public void createSecurityTable() {
		ArrayList<Pair> attributes = new ArrayList<Pair>();
		attributes.add(new Pair("uid", "TEXT"));
		attributes.add(new Pair("securityQuestion", "TEXT"));
		attributes.add(new Pair("securityAnswer", "TEXT"));
		createTable(securityTable, attributes);
	}

	public void resetAllTables() { // used with cautions! all existing data will be cleared
		String queryString;
		queryString = "DROP TABLE IF EXISTS " + quizTable + ";";
		connector.execute(queryString);
		createQuizTable();
		queryString = "DROP TABLE IF EXISTS " + userTable + ";";
		connector.execute(queryString);
		createUserTable();
		queryString = "DROP TABLE IF EXISTS " + historyTable + ";";
		connector.execute(queryString);
		createHistoryTable();
		queryString = "DROP TABLE IF EXISTS " + questionTable + ";";
		connector.execute(queryString);
		createQuestionTable();
		queryString = "DROP TABLE IF EXISTS " + relationTable + ";";
		connector.execute(queryString);
		createRelationTable();
		queryString = "DROP TABLE IF EXISTS " + messageTable + ";";
		connector.execute(queryString);
		createMessageTable();
		queryString = "DROP TABLE IF EXISTS " + announceTable + ";";
		connector.execute(queryString);
		createAnnouncementTable();
		queryString = "DROP TABLE IF EXISTS " + reviewTable + ";";
		connector.execute(queryString);
		createReviewTable();
		queryString = "DROP TABLE IF EXISTS " + banTable + ";";
		connector.execute(queryString);
		createBanTable();
		queryString = "DROP TABLE IF EXISTS " + securityTable + ";";
		connector.execute(queryString);
		createSecurityTable();
	}
	
	public DBInterface() {
		connector = new DBConnection();
		connector.makeConnection();
	}
	
	public void DBShutDown() {
		connector.shutdownConnection();
	}
	
	public void createTableIfNecessary() {
		ResultSet rs = connector.executeQuery("SHOW TABLES LIKE \"Users\"");
		try {
			if (!rs.first()) {
				resetAllTables();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<Quiz.QuizAbstract> getListOfQuizWithQuery(String query) {
		ArrayList<Quiz.QuizAbstract> list = new ArrayList<Quiz.QuizAbstract>();
		//String query = "SELECT title, category, description, createTime, creator FROM " + quizTable + " " + condition + ";";
		ResultSet rs = connector.executeQuery(query);
		try {
			while (rs.next()) {
				String title = rs.getString("title");
				String category = rs.getString("category");
				String description = rs.getString("description");
				String createTime = rs.getString("createTime");
				String creator = rs.getString("creator");
				String tagString = rs.getString("tags");
				boolean allowPractice = rs.getString("allowPractice").equals("true");
				boolean onePage = rs.getString("onePage").equals("true");
				boolean random = rs.getString("random").equals("true");
				boolean immediateCorrection = rs.getString("immediateCorrection").equals("true");
				String[] tagArray = tagString.split(Question.delim); 
				ArrayList<String> tags = new ArrayList<String>();
				for (int i = 0; i < tagArray.length; i++) {
					tags.add(tagArray[i]);
				}
				list.add(new Quiz.QuizAbstract(title, category, description, createTime, creator, tags, allowPractice, onePage, random, immediateCorrection));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	public ArrayList<Quiz.QuizAbstract> getListOfQuizInfo() {
		String query = "SELECT * FROM " + quizTable + ";";;
		return getListOfQuizWithQuery(query);
	}
	
	
	public Quiz.QuizAbstract getQuizInfoByTitle(String title) {
		String query = "SELECT * FROM " + quizTable + " WHERE title = \"" + title + "\";";
		////System.out.println(getListOfQuizWithQuery(query).size());
		ArrayList<Quiz.QuizAbstract> quizAbstract = getListOfQuizWithQuery(query);
		return quizAbstract.size() == 0?null: quizAbstract.get(0);
	}
	
	public ArrayList<Quiz.QuizAbstract> getMostRecentlyCreatedQuizzes(int maxCount) {
		String query = "SELECT * FROM " + quizTable + " " + "ORDER BY createTime DESC LIMIT " + maxCount + ";";
		return getListOfQuizWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizAbstract> getUserQuizCreatingActivities(String username) {
		String query = "SELECT * FROM " + quizTable + " " + "WHERE creator = \"" + username + "\" ORDER BY createTime DESC;"; 
		return getListOfQuizWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizAbstract> getPopularQuizzes(int maxCount) {
		String query = "SELECT * FROM " + historyTable + ", " + quizTable + " WHERE " + historyTable + ".title = " + quizTable + ".title GROUP BY " + historyTable + ".title ORDER BY count(*) DESC LIMIT " + maxCount + ";";
		return getListOfQuizWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizAbstract> getPopularQuizzesByReviews(int maxCount) {
		String query = "SELECT * FROM " + reviewTable + ", " + quizTable + " WHERE " + reviewTable + ".title = " + quizTable + ".title GROUP BY " + reviewTable + ".title ORDER BY count(*) DESC LIMIT " + maxCount + ";";
		return getListOfQuizWithQuery(query);
	}
	
	private ArrayList<Quiz.QuizTakingActivity> getListOfActivitiesWithQuery(String query) {
		ArrayList<Quiz.QuizTakingActivity> activities = new ArrayList<Quiz.QuizTakingActivity>();
		ResultSet rs = connector.executeQuery(query);
		try {
			while (rs.next()) {
				String username = rs.getString("uid");
				String title = rs.getString("title");
				float score = rs.getFloat("score");
				String completeTime = rs.getString("completeTime");
				String takingTime = rs.getString("recordTime");
				activities.add(new Quiz.QuizTakingActivity(title, username, score, completeTime, takingTime));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activities;
	}

	public ArrayList<Quiz.QuizTakingActivity> getUserQuizTakingActivities(String username) {
		String query = "SELECT * FROM " + historyTable + " WHERE uid = \"" + username + "\" ORDER BY recordTime DESC;";
		return getListOfActivitiesWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizTakingActivity> getQuizTakingActivities(String quizTitle) {
		String query = "SELECT * FROM " + historyTable + " WHERE title = \"" + quizTitle + "\" ORDER BY recordTime DESC;";
		return getListOfActivitiesWithQuery(query);
	} 
	
	public ArrayList<Quiz.QuizTakingActivity> getListOfTopPerformersOfAllTime(int maxCount) {
		String query = "SELECT * FROM " + historyTable + " ORDER BY score DESC, completeTime LIMIT " + maxCount + ";";
		return getListOfActivitiesWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizTakingActivity> getListOfTopPerformersForQuiz(String title, int maxCount) {
		String query = "SELECT * FROM " + historyTable + " WHERE title = \"" + title + "\" ORDER BY score DESC, completeTime LIMIT " + maxCount + ";";
		return getListOfActivitiesWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizTakingActivity> getListOfTopPerformersForPeriod(String title, long durationInMillis) {
		long prevMillis = System.currentTimeMillis() - durationInMillis;
		Date prevDate = new Date(prevMillis);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String prevTime = ft.format(prevDate);
		String query = "SELECT * FROM " + historyTable + " WHERE title = \"" + title + "\" AND recordTime > \"" + prevTime +"\"; ";
		return getListOfActivitiesWithQuery(query);
	}
	
	public ArrayList<Quiz.QuizTakingActivity> getUserQuizTakingActivitiesForQuiz(String username, String title, String orderBy, int maxCount) {
		// orderBy can be 1. score, 2. completeTime (time spent by the user when took the quiz), 3. recordTime (time took the quiz) 
		//System.out.println(orderBy);
		String query = "SELECT * FROM " + historyTable + " WHERE uid = \"" + username + "\" AND title = \"" + title + "\" ORDER BY " + orderBy;
		if (orderBy.equals("score") || orderBy.equals("recordTime")) {
			query += " DESC";
		} 
		query += " LIMIT " + maxCount + ";";
		//System.out.println(query);
		return getListOfActivitiesWithQuery(query);
	}
	
	public void changePassword(String username, String newPassword) {
		connector.execute("UPDATE " + userTable +  " SET password = \"" + newPassword + "\" WHERE uid = \"" + username + "\";");
	}
	
	public String getSecurityQuestion(String username) {
		ResultSet rs = connector.executeQuery("SELECT securityQuestion FROM " + securityTable + " WHERE uid = \"" + username + "\";");
		String securityQuestion = null;
		try {
			if (rs.next()) {
				securityQuestion = rs.getString("securityQuestion");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return securityQuestion;
	}
	
	public boolean hasSecurityQuestion(String username) {
		ResultSet rs = connector.executeQuery("SELECT * FROM " + securityTable + " WHERE uid = \"" + username + "\";");
		try {
			if (rs.first()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void setSecurityQuestion(String username, String securityQuestion, String securityAnswer) {
		if(hasSecurityQuestion(username)) {
			connector.execute("UPDATE " + securityTable + " SET securityQuestion = \"" + securityQuestion + "\", securityAnswer = \"" + securityAnswer + "\" WHERE uid = \"" + username + "\";");
		} else {
			connector.execute("INSERT INTO " + securityTable + " VALUES (" + username + ", " + securityQuestion + ", " + securityAnswer + ")");
		}
	}
	
	public boolean checkSecurity(String username, String securityAnswer) {
		ResultSet rs = connector.executeQuery("SELECT securityAnswer FROM " + securityTable + " WHERE uid = \"" + username + "\";");
		try {
			if (rs.first()) {
				if (securityAnswer.equals(rs.getString("securityAnswer"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void reportUser(String user, String reporter, String reason) {
		sendMessage(reporter, "wwang23", reporter + " reports user: " + user + " for being inappropriate for the following reason: " + reason);
	}
	
	public void reportQuiz(String title, String reporter, String reason) {
		sendMessage(reporter, "wwang23", reporter + " reports quiz: " + title + " for being inappropriate for the following reason: " + reason);
	}
	
	public void banUser(String username, long banTimeInMillis) {
		
		long banMillis = System.currentTimeMillis() + banTimeInMillis;
		Date banDate = new Date(banMillis);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String banTime = ft.format(banDate);
		String query = null;
		ResultSet rs = connector.executeQuery("SELECT uid FROM " + banTable + " WHERE uid = \"" + username + "\";");
		try {
			if (rs.first()) {
				query = "UPDATE " + banTable + " SET bannedUntil = \"" + banTime + "\" WHERE uid = \"" + username + "\";" ;
			} else {
				query = "INSERT INTO " + banTable + " VALUES (\"" + username + "\", \"" + banTime + "\");";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(query);
		connector.execute(query);
	}
	
	public String getBannedTime(String username) {
		ResultSet rs = connector.executeQuery("SELECT bannedUntil FROM " + banTable + " WHERE uid = \"" + username + "\";");
		String time = null;
		try {
			rs.first();
			time = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	
	public boolean isBanned(String username) {
		ResultSet rs = connector.executeQuery("SELECT bannedUntil FROM " + banTable + " WHERE uid = \"" + username + "\";");
		try {
			if (rs.first()) {
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = ft.parse(rs.getString(1));
				if (date.after(new Date())) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public float getUserTopScoreOnQuiz(String username, String title) {
		String query = "SELECT * FROM " + historyTable + " WHERE uid = \"" + username + "\" AND title =  \"" + title + "\" ORDER BY score DESC;";
		return getListOfActivitiesWithQuery(query).get(0).getScore();
	}
	
	public void addNewAnnouncement(String announcement, String administrator) {
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(announcement);
		attributes.add(administrator);
		attributes.add(getCurrentTimeStamp());
		connector.insertQuery(announceTable, attributes);
	}
	
	public ArrayList<Announcement> getAnnouncements() {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		ResultSet rs = connector.executeQuery("SELECT * FROM " + announceTable + " ORDER BY announceTime DESC;");
		try {
			while (rs.next()) {
				String content = rs.getString("announcement");
				String administrator = rs.getString("administrator");
				String announceTime = rs.getString("announceTime");
				announcements.add(new Announcement(content, administrator, announceTime));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return announcements;
	}
	
	public void deleteAnnouncement(Announcement announce) {
		connector.execute("DELECT FROM " + announceTable + " WHERE announcement = \"" + announce.getContent() + "\";");
	}
	
	private void removeQuiz(String title) {
		connector.execute("DELETE FROM " + quizTable + " WHERE title = \"" + title + "\";");
	}
	
	public String getQuizCreator(String title) {
		ResultSet rs = connector.executeQuery("SELECT creator FROM " + quizTable + " WHERE title = \"" + title + "\";");
		try {
			if (rs.first()) {	
				return rs.getString("creator");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean editQuizAbstract(String username, String oldQuizTitle, Quiz.QuizAbstract quizAbstract) {	
		if (getQuizCreator(oldQuizTitle).equals(username)) {
			String newTitle = quizAbstract.getTitle();
			String newCategory = quizAbstract.getCategory();
			String newDescription = quizAbstract.getDescription();
			String newCreator = quizAbstract.getCreator();
			String allowPractice = quizAbstract.isPracticeAllowed()?"true":"false";
			String onePage = quizAbstract.isOnePage()? "true":"false";
			String random = quizAbstract.isRandom()?"true":"false";
			String immediateCorrection = quizAbstract.isImmediateCorrection()? "true":"false";
			String tags = Question.encode(quizAbstract.getTags());
			//System.out.println("!!!!" + tags);
			StringBuilder query = new StringBuilder();
			query.append("UPDATE " + quizTable + " SET title = \"" + newTitle);
			query.append( "\", category = \"" + newCategory);
			query.append( "\", description = \"" + newDescription);
			query.append( "\", createTime = \"" + getCurrentTimeStamp());
			query.append( "\", creator = \"" + newCreator);
			query.append( "\", allowPractice = \"" + allowPractice);
			query.append( "\", onePage = \"" + onePage);
			query.append( "\", random = \"" + random);
			query.append( "\", immediateCorrection = \"" + immediateCorrection);
			query.append( "\", tags = \"" + tags);
			query.append( "\" WHERE title = \"" + oldQuizTitle + "\";");
			connector.executeUpdate(query.toString());
			
			if (!oldQuizTitle.equals(newTitle)) {
				connector.execute("UPDATE " + historyTable + " SET title = \"" + newTitle + "\" WHERE title = \"" + oldQuizTitle + "\";");
			}
			
			return true;
		}
		return false;
	}
	
	public boolean editQuizQuestion(String username, String oldQuizTitle, Quiz editedQuiz) {
		////System.out.println("user is " + username);
		////System.out.println("creator is " + getQuizCreator(oldQuizTitle));
		if (getQuizCreator(oldQuizTitle).equals(username)) {
			removeQuiz(oldQuizTitle);
			addNewQuiz(editedQuiz);
			
			if (!oldQuizTitle.equals(editedQuiz.getTitle())) {
				connector.execute("UPDATE " + historyTable + " SET title = \"" + editedQuiz.getTitle() + "\" WHERE title = \"" + oldQuizTitle + "\";");
			}
			
			return true;
		}
		return false;
	}
	
	public void deleteQuiz(String administrator, String title) {
		if (isAdministrator(administrator)) {
			connector.execute("DELETE FROM " + quizTable + " WHERE title = \"" + title + "\";");
			connector.execute("DELETE FROM " + historyTable + " WHERE title = \"" + title + "\";");
		}
	}
	
	public void deleteAccount(String administrator, String username) {
		if (isAdministrator(administrator)) {
			connector.execute("DELETE FROM " + userTable + " WHERE uid = \"" + username + "\";");
			connector.execute("DELETE FROM " + historyTable + " WHERE uid = \"" + username + "\";");
			connector.execute("DELETE FROM " + relationTable + " WHERE uid1 = \"" + username + "\" OR uid2 = \"" + username + "\";");
		}
	}
	
	public void promoteAdministrator(String administrator, String username) {
		if (isAdministrator(administrator)) {
			connector.executeUpdate("UPDATE " + userTable + " SET priority = \"administrator\" WHERE uid =\"" + username + "\";");
		}
	}
	
	public int getTotalNumberOfQuizTaken() {
		ResultSet rs = connector.executeQuery("SELECT COUNT(DISTINCT title) FROM " + historyTable + ";");
		int qc;
		////System.out.println("in add new question");
		try {
			rs.first();
			qc = rs.getInt(1);
			////System.out.println(qid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return qc;
	}
	
	public int addNewQuestion(Question question) {
		ResultSet rs = connector.executeQuery("SELECT COUNT(*) FROM " + questionTable + ";");
		int qid;
		////System.out.println("in add new question");
		try {
			rs.first();
			qid = rs.getInt(1);
			////System.out.println(qid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}	
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(qid);
		attributes.add(question.getType());
		attributes.add(question.getDescription());
		attributes.add(question.getAnswer());
		connector.insertQuery(questionTable, attributes);
		return qid;
	}
	
	public boolean isExistingUser(String username) {
		ResultSet rs = connector.executeQuery("SELECT uid FROM " + userTable + " WHERE uid = \"" + username + "\";");
		try {
			if (!rs.first()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// add a new achievement to user (achievement should not be gotten before)
	public void addNewAchievement(String username, String achievement) {
		ResultSet rs = connector.executeQuery("SELECT achievements FROM " + userTable + " WHERE uid = \"" + username + "\";");
		String achievements = null;
		try {
			if (!rs.first()) return;
			achievements = rs.getString(1);
			if (achievements.equals("")) {
				achievements = achievement;
			} else {
				achievements += Question.delim + achievement;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		connector.executeUpdate("UPDATE " + userTable + " SET achievements = \"" + achievements + "\" WHERE uid = \"" + username + "\";");
	}
	
	public ArrayList<String> getUserAchievements(String username) {
		ResultSet rs = connector.executeQuery("SELECT achievements FROM " + userTable + " WHERE uid = \"" + username + "\";");
		ArrayList<String> achievements = new ArrayList<String> ();
		try {
			if (rs.first()) {
				for (String achievement: rs.getString(1).split(Question.delim)) {
					achievements.add(achievement);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achievements;
	}
	
	// return true if add successfully, false is username is already taken
	public boolean addNewUser(String username, String password, boolean isAdministrator) {
		if (isExistingUser(username)) return false;
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(username);
		attributes.add(password);
		attributes.add(""); // initial empty achievements
		attributes.add(isAdministrator? "administrator": "normal");
		connector.insertQuery(userTable, attributes);
		return true;
	}
	
	public ArrayList<String> addNewQuiz(Quiz quiz) {
		return addNewQuiz(quiz, "wwang23");
	}
	
	// return false if quiz already exist, otherwise return true;
	public ArrayList<String> addNewQuiz(Quiz quiz, String creator) {
		StringBuilder sb = new StringBuilder();
		for (Question question: quiz.getQuestions()) {
			int qid = addNewQuestion(question);
			sb.append(qid + Question.delim);
		}
		for (int i = 0; i < Question.delim.length() && sb.length() > 0; i++) {
			sb.deleteCharAt(sb.length()-1);
		}
		////System.out.println(sb.toString());
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(quiz.getTitle());
		attributes.add(quiz.getCategory());
		attributes.add(quiz.getDescription());
		attributes.add(quiz.isPracticeAllowed()? "true": "false");
		attributes.add(sb.toString());
		attributes.add(creator);
		attributes.add(getCurrentTimeStamp());
		sb = new StringBuilder();
		for (String tag: quiz.getTags()) {
			sb.append(tag + Question.delim);
		}
		for (int i = 0; i < Question.delim.length() && sb.length() > 0; i++) {
			sb.deleteCharAt(sb.length()-1);
		}
		attributes.add(sb.toString());
		attributes.add(quiz.isOnePage()? "true": "false");
		attributes.add(quiz.isRandom()? "true": "false");
		attributes.add(quiz.isImmediateCorrection()? "true": "false");
		connector.insertQuery(quizTable, attributes);
		ArrayList<String> achievements = calculateNewCreatorAchievements(creator);
		if (achievements.size() != 0) {
			for (String achievement: achievements) {
				addNewAchievement(creator, achievement);
			}
		}
		return achievements;
	}

	ArrayList<String> calculateNewCreatorAchievements(String username) {
		ArrayList<String> achievements = new ArrayList<String>();
		ResultSet rs = connector.executeQuery("SELECT COUNT(*) FROM " + quizTable + " WHERE creator =\"" + username + "\";");
		try {
			if (rs.first()) {
				int count = rs.getInt(1);
				if (count == 1) {
					String achievement = "Amateur Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}
				} else if (count == 5) {
					String achievement = "Prolific Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}					
				} else if (count == 10) {
					String achievement = "Prodigious Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achievements;
	}
	
	private String getCurrentTimeStamp() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(new Date());
	}
	
	// check if the provided username and password information is correct
	public boolean isValidUser(String username, String password) {
		ResultSet rs = connector.executeQuery("SELECT * FROM " + userTable + " WHERE uid = \"" + username + "\";");
		try {
			if (!rs.first()) {
				return false;
			}
			if (!rs.getString("password").equals(password)) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public ArrayList<Question> getQuestionsForQuiz(String title) {
		ArrayList<Question> questions = new ArrayList<Question> ();
		ResultSet rs = connector.executeQuery("SELECT questions FROM " + quizTable + " WHERE title = \"" + title + "\";");
		try {
			if (rs.first()) {
				String qstr = rs.getString("questions");
				String[] qarray = qstr.split(Question.delim);
				int qstart = Integer.parseInt(qarray[0]); 
				int qend = Integer.parseInt(qarray[qarray.length-1]); 
				rs = connector.executeQuery("SELECT * From " + questionTable + " WHERE qid >= " + qstart + " AND qid <= " + qend + ";");
				while (rs.next()) {
					String type = rs.getString("type");
					String description = rs.getString("description");
					String answer = rs.getString("answer");
					Question q = null;
					if (type.equals("question-response")) {
						q = new QuestionResponse(description, answer);
					} else if (type.equals("fill-in-blank")) {
						q = new FillInBlank(description, answer);
					} else if (type.equals("multiple-choice")) {
						q = new MultipleChoice(description, answer);
					} else if (type.equals("picture-response")) {
						q = new PictureResponse(description, answer);
					} else if (type.equals("matching")) {
						q = new Matching(description, answer);
					} else if (type.equals("multi-answer")) {
						q = new MultiAnswer(description, answer);
					} else if (type.equals("multiple-choice-with-multiple-answer")) {
						q = new MultipleChoiceWithMultipleAnswer(description, answer);
					} else if (type.equals("picutre-response")) {
						q = new PictureResponse(description, answer);
					} else {
						throw new RuntimeException("Unsupported question type!"); 
					}
					questions.add(q);
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}
	
	public int getTotalNumberOfUsers() {
		ResultSet rs = connector.executeQuery("SELECT COUNT(*) FROM " + userTable + ";");
		int count;
		////System.out.println("in add new question");
		try {
			rs.first();
			count = rs.getInt(1);
			////System.out.println(qid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}	
		return count;
	}
	
	public void loadQuizzes(String folderPath) {
		File folder = new File(folderPath);
		if (folder.isDirectory()) {
			for (File fileEntry:folder.listFiles()) {
				if (fileEntry.getName().endsWith(".xml")) {
					addNewQuiz(XMLQuizParser.parseFile(folderPath + fileEntry.getName()));
				}
			}
		} else {
			throw new RuntimeException("Invalid folder path!");
		}
	}
	
	public void clearQuizHistory(String title) {
		connector.execute("DELETE FROM " + historyTable + " WHERE title = \"" + title + "\";");
	}
	
	public boolean isAdministrator(String username) {
		ResultSet rs = connector.executeQuery("SELECT * FROM " + userTable + " WHERE uid = \"" + username + "\";");
		try {
			if (!rs.first()) {
				return false;
			}
			
			if (!rs.getString("priority").equals("administrator")) { 
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	} 
	
	public ArrayList<String> searchUser(String keyword) {
		ResultSet rs = connector.executeQuery("SELECT uid FROM " + userTable + " WHERE uid LIKE \"%" + keyword + "%\";");
		ArrayList<String> users = new ArrayList<String>();
		try {
			while (rs.next()) {
				users.add(rs.getString("uid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<Quiz.QuizAbstract> searchQuiz(String keyword) { // with title or tag
		String query = "SELECT * FROM " + quizTable + " WHERE title LIKE \"%" + keyword + "%\" OR category LIKE \"%" + keyword + "%\" OR tags LIKE  \"%" + keyword + "%\";";
		//System.out.println(query);
		return getListOfQuizWithQuery(query);
	}
	
	public void removeFriend(String user1, String user2) {
		connector.execute("DELETE FROM " + relationTable + " WHERE uid1 = \"" + user1 + "\" AND uid2 = \"" + user2 + "\";");
		connector.execute("DELETE FROM " + relationTable + " WHERE uid1 = \"" + user2 + "\" AND uid2 = \"" + user1 + "\";");
	}
	
	public ArrayList<String> addNewActivity(String username, String quizTitle, int score, long completeTimeInMillis, boolean isPracticeMode) {
		ArrayList<String> achievements = new ArrayList<String> ();
		if (isPracticeMode) {
			////System.out.println("haha");
			String achievement = "Practice Makes Perfect";
			if (!hasAchievement(username, achievement)) {
				////System.out.println("hehe");
				achievements.add(achievement);
				addNewAchievement(username, achievement);
			}
			////System.out.println(achievements.size());
			return achievements;
		}
		long second = (completeTimeInMillis/1000) % 60;
		long minute = (completeTimeInMillis/(1000*60)) % 60;
		long hour = (completeTimeInMillis/(1000*60*60)) % 24;
		String completeTime = String.format("%02d:%02d:%02d", hour, minute, second);
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(username);
		attributes.add(quizTitle);
		attributes.add(score);
		attributes.add(completeTime);
		attributes.add(getCurrentTimeStamp());
		connector.insertQuery(historyTable, attributes);
		achievements = calculateNewQuiztakerAchievements(username, quizTitle, score, isPracticeMode);
		if (achievements.size() != 0) {
			for (String achievement: achievements) {
				addNewAchievement(username, achievement);
			}
		}
		return achievements;
	}
	
	public boolean isFriend(String user1, String user2) {
		ResultSet rs = connector.executeQuery("SELECT * FROM " + relationTable + " WHERE uid1 = \"" + user1 + "\" AND uid2 = \"" + user2 + "\";");
		try {
			if (!rs.first()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void addFriend(String user1, String user2) {
		if (isFriend(user1, user2)) return;
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(user1);
		attributes.add(user2);
		connector.insertQuery(relationTable, attributes);
		ArrayList<Object> attributes2 = new ArrayList<Object>();
		attributes2.add(user2);
		attributes2.add(user1);		
		connector.insertQuery(relationTable, attributes2);
	}
	
	public void sendMessage(String fromUser, String toUser, String message) {
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(toUser);
		attributes.add(fromUser);
		attributes.add(message);
		attributes.add("unread");
		attributes.add(getCurrentTimeStamp());
		connector.insertQuery(messageTable, attributes);
	}
	
	private ArrayList<Message> getListOfMessagesWithQuery(String query) {
		ArrayList<Message> messages = new ArrayList<Message>();
		ResultSet rs = connector.executeQuery(query);
		try {
			while (rs.next()) {
				String toUid = rs.getString("toUid");
				String fromUid = rs.getString("fromUid");
				String message = rs.getString("message");
				boolean unread = rs.getString("status").equals("unread");
				String sendTime = rs.getString("sendTime");
				messages.add(new Message(toUid, fromUid, message, unread, sendTime));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messages;
	}
	
	public ArrayList<Message> getAllMessages(String username) {
		String query = "SELECT * FROM " + messageTable + " WHERE toUid = \"" + username + "\" ORDER BY sendTime DESC;";
		return getListOfMessagesWithQuery(query);
	}
	
	public ArrayList<Message> getNewMessages(String username) {
		String query = "SELECT * FROM " + messageTable + " WHERE toUid = \"" + username + "\" AND status = \"unread\" ORDER BY sendTime DESC;";
		return getListOfMessagesWithQuery(query);
	}
 	
	public void markMessageRead(Message message) {
		connector.execute("UPDATE " + messageTable + " SET status = \"read\" WHERE toUid = \"" + message.getRecepient() + "\" AND fromUid = \"" + message.getSender() + "\" AND sendTime = \"" + message.getTime() + "\";");
	}
	
	public void markAllMessagesRead(String username) {
		connector.execute("UPDATE " + messageTable + " SET status = \"read\" WHERE toUid = \"" + username + "\";");
	}
	
	public ArrayList<Review> getListOfReviewsWithQuery(String query) {
		ArrayList<Review> reviews = new ArrayList<Review> ();
		ResultSet rs = connector.executeQuery(query);
		try {
			while (rs.next()) {
				String username = rs.getString("reviewer");
				String title = rs.getString("title");
				String reviewTime = rs.getString("reviewTime");
				String review = rs.getString("review");
				reviews.add(new Review(title, username, review, reviewTime));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviews;
	}
	
	public ArrayList<Review> getListOfRecentReviews(int maxCount) {
		String query = "SELECT * FROM " + reviewTable + " ORDER BY reviewTime DESC LIMIT " + maxCount + ";";
		return getListOfReviewsWithQuery(query);
	}
	
	public ArrayList<Review> getReviewsForQuiz(String title, int maxCount) {
		String query = "SELECT * FROM " + reviewTable + " WHERE title = \"" + title + "\" ORDER BY reviewTime DESC LIMIT " + maxCount + ";";
		return getListOfReviewsWithQuery(query);
	}
	
	public ArrayList<Review> getUserReviews(String username, int maxCount) {
		String query = "SELECT * FROM " + reviewTable + " WHERE reviewer = \"" + username + "\" ORDER BY reviewTime DESC LIMIT " + maxCount + ";";
		return getListOfReviewsWithQuery(query);
	}
	
	public ArrayList<String> recentTestTakerForQuiz(String title, int maxCount) {
		ResultSet rs = connector.executeQuery("SELECT uid FROM " + historyTable + " WHERE title = \"" + title + "\" GROUP BY uid ORDER BY recordTime DESC LIMIT " + maxCount + ";");
		ArrayList<String> testTakers = new ArrayList<String> ();
		try {
			while (rs.next()) {
				testTakers.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testTakers;
	}
	
	public void addNewReview(String title, String username, String review) {
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(title);
		attributes.add(username);
		attributes.add(review);
		attributes.add(getCurrentTimeStamp());
		connector.insertQuery(reviewTable, attributes);
	}
	
	public ArrayList<String> getFriend(String username) {
		ResultSet rs = connector.executeQuery("SELECT uid2 FROM " + relationTable + " WHERE uid1 = \"" + username + "\";");
		ArrayList<String> friends = new ArrayList<String>();
		try {
			while (rs.next()) {
				friends.add(rs.getString("uid2"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTag(String title, String tag) {
		ResultSet rs = connector.executeQuery("SELECT tags FROM " + quizTable + " WHERE title = \"" + title + "\";");
		String tags = "";
		try {
			if (rs.first()) {
				tags = rs.getString("tags");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tags.equals("")) {
			tags = tag;
		} else {
			tags += Question.delim + tag;
		}
		connector.executeUpdate("UPDATE " + quizTable + " SET tags = \"" + tags + "\" WHERE title = \"" + title + "\";");
	}
	
	public ArrayList<String> getListOfUsers() {
		ArrayList<String> users = new ArrayList<String> ();
		ResultSet rs = connector.executeQuery("SELECT uid From " + userTable + ";");
		try {
			while (rs.next()) {
				String user = rs.getString("uid");
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean hasAchievement(String username, String achievement) {
		ArrayList<String> achievements = getUserAchievements(username);
		for (String achieve: achievements) {
			////System.out.println("want to add:" + achievement);
			if (achieve.equals(achievement)) {
				////System.out.println("equal");
				return true;
			}
		}
		////System.out.println("not equal");
		return false;
	}
	
	
	ArrayList<String> calculateNewQuiztakerAchievements(String username, String quizTitle, float score, boolean isPracticeMode) {
		ArrayList<String> achievements = new ArrayList<String>();
		ResultSet rs = connector.executeQuery("SELECT COUNT(*) FROM " + historyTable + " WHERE uid =\"" + username + "\";");
		try {
			if (rs.first()) {
				int count = rs.getInt(1);
				if (count == 1) {
					String achievement = "Beginner";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}
				} else if (count == 10) {
					String achievement = "Quiz Machine";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = connector.executeQuery("SELECT score FROM " + historyTable + " WHERE title = \"" + quizTitle + "\" ORDER BY score DESC LIMIT 1;");
		try {
			if (rs.first()) {
				if (score == rs.getFloat(1)) {
					String achievement = "I am the Greatest";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achievements;		
	}
	
	public float querySingleNumber(String query) {
		ResultSet rs = connector.executeQuery(query);
		float ret = 0;
		try {
			if (rs.first()) {
				ret = rs.getFloat(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String getQuizStatistics(String title) {
		float average = querySingleNumber("SELECT AVG(score) FROM " + historyTable + " WHERE title = \"" + title + "\";");
		float count = querySingleNumber("SELECT COUNT(*) FROM " + historyTable + " WHERE title = \"" + title + "\";");
		float max = querySingleNumber("SELECT MAX(score) FROM " + historyTable + " WHERE title = \"" + title + "\";");
		float min = querySingleNumber("SELECT MIN(score) FROM " + historyTable + " WHERE title = \"" + title + "\";");
		StringBuilder sb = new StringBuilder();
		sb.append("There are " + count + " users took the quiz with a max score = " + max + ", min score = " + min + ", and average score = " + average);
		return sb.toString();
	}
	
	public ArrayList<String> calculateNewCreaterAchievements(String username) {
		ArrayList<String> achievements = new ArrayList<String>();
		ResultSet rs = connector.executeQuery("SELECT COUNT(*) FROM " + quizTable + " WHERE creator =\"" + username + "\";");
		try {
			if (rs.first()) {
				int count = rs.getInt(1);
				if (count == 1) {
					String achievement = "Amateur Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}
				} else if (count == 5) {
					String achievement = "Prolific Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}					
				} else if (count == 10) {
					String achievement = "Prodigious Author";
					if (!hasAchievement(username, achievement)) {
						achievements.add(achievement);
					}					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return achievements;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBInterface dbInterface = new DBInterface();
		//dbInterface.getUser
		//dbInterface.loadQuizzes("Quizzes/");
		/*
		dbInterface.resetAllTables();
		dbInterface.createTableIfNecessary();
		dbInterface.loadQuizzes("Quizzes/");
		
		dbInterface.addNewUser("wwang23", "12345", true);
		dbInterface.addNewUser("david", "23456", false);
		//System.out.println(dbInterface.isValidUser("wwang23", "12345"));
		//System.out.println(dbInterface.isValidUser("wwang23", "23456"));
		//System.out.println(dbInterface.isValidUser("www", "12345"));
		//System.out.println(dbInterface.isAdministrator("wwang23"));
		//System.out.println(dbInterface.isAdministrator("wwan3"));
		//System.out.println(dbInterface.getTotalNumberOfUsers());
		dbInterface.addNewAchievement("wwang23", "Prodigious Author");
		dbInterface.addNewAchievement("wwang23", "Quiz Machine");
		dbInterface.addNewAchievement("wwang23", "Creator");
		dbInterface.addNewAchievement("wwg23", "Creator");
		dbInterface.addNewAchievement("david", "Amateur");
		//System.out.println(dbInterface.getUserAchievements("wwang23"));
		//System.out.println(dbInterface.getMostRecentlyCreatedQuizzes(3));
		//System.out.println(dbInterface.getListOfQuizInfo());
		//System.out.println(dbInterface.getUserQuizCreatingActivities("david"));
		
		dbInterface.addNewActivity("wwang23", "Cities", 100, 5*60*1000+1231);
		sleep(1000);
		dbInterface.addNewActivity("wwang23", "Cities", 100, 4*60*1000+1231);
		sleep(1000);
		dbInterface.addNewActivity("wwang23", "Bunny Quiz", 100, 7*64*1000+1231);
		sleep(1000);
		dbInterface.addNewActivity("david", "Stanford Quiz", 95, 15*60*1000+343556);
		sleep(1000);
		dbInterface.addNewActivity("david", "Cities", 98, 10*60*1000+3556);
		//System.out.println(dbInterface.getUserQuizTakingActivities("wwang23"));
		
		//System.out.println(dbInterface.getUserQuizCreatingActivities("wwang23"));
		dbInterface.addNewUser("frank", "666", false);
		dbInterface.addFriend("wwang23", "david");
		dbInterface.addFriend("wwang23", "david");
		dbInterface.addFriend("wwang23", "frank");
		//System.out.println(dbInterface.isFriend("wwang23", "david"));
		//System.out.println(dbInterface.isFriend("frank", "david"));
		
		dbInterface.sendMessage("wwang23", "david", "Hey david, how's it going?");
		dbInterface.sendMessage("wwang23", "frank", "Hey frank, how are you?");
		dbInterface.sendMessage("frank", "wwang23", "Great!");
		dbInterface.sendMessage("david", "wwang23", "Hey dude!");
		
		dbInterface.markMessageRead(dbInterface.getNewMessages("wwang23").get(0));
		//System.out.println(dbInterface.getNewMessages("wwang23"));
		dbInterface.markAllMessagesRead("wwang23");
		//System.out.println(dbInterface.getPopularQuizzes(2));
		//System.out.println(dbInterface.getListOfTopPerformersForQuiz("Cities", 2));
		//System.out.println(dbInterface.getQuizTakingActivities("Cities"));
		dbInterface.addNewActivity("frank", "Cities", 94, 10*46*1000+1231);

		
		dbInterface.deleteQuiz("wwang23", "Continents By Population");
		dbInterface.deleteQuiz("wwang23", "test2");
		//System.out.println(dbInterface.getTotalNumberOfQuizTaken());
		dbInterface.promoteAdministrator("wwang23", "frank");
		
		dbInterface.addNewAnnouncement("System maintainence tomorrow", "wwang23");
		sleep(5000);
		dbInterface.addNewAnnouncement("All user will be added for 100 points", "wwang23");
		sleep(5000);
		dbInterface.addNewAnnouncement("Just kidding!", "wwang23");
		//System.out.println(dbInterface.getAnnouncements());
		dbInterface.addTag("Cities", "nonsence");
		dbInterface.addTag("Cities", "city");
		//System.out.println(dbInterface.getQuizInfoByTitle("Cities"));
		dbInterface.addNewReview("Challenges", "wwang23", "Great! Try it!");
		dbInterface.addNewReview("Bunny Quiz", "david", "Sucks!");
		dbInterface.addNewReview("Capitals", "frank", "Interesting");
		
		//System.out.println(dbInterface.getPopularQuizzesByReviews(4));
		
		////System.out.println(dbInterface.getListOfQuizInfo());
		ArrayList<String> test = new ArrayList<String>();
		test.add("AA");
		//System.out.println(test.size());
		test.remove(0);
		//System.out.println(test.size());*/
		dbInterface.DBShutDown();
	}
}
