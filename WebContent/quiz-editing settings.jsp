<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Quiz Setting</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<h1>Edit Quiz Setting</h1>
<br/><br/>
<% 

DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
String quizTitle = request.getParameter("quiz title");

if (quizTitle ==null){
	quizTitle = (String)session.getAttribute("quiz to be edited");
	
}
//System.out.println("question setting"+quizTitle);




Quiz.QuizAbstract quiz = db.getQuizInfoByTitle(quizTitle);
String title = quiz.getTitle();
String description = quiz.getDescription();
boolean isRandom = quiz.isRandom();
String randomOrder = (isRandom)?"yes":"no";
boolean isOnePage = quiz.isOnePage();
String quizDisplay = (isOnePage)?"yes":"no";
String category = quiz.getCategory();
ArrayList<String> tags = quiz.getTags();
String tag = "";
for (int i = 0; i < tags.size(); i++){
	tag += tags.get(i);
	if (i != tags.size() - 1)
		tag += ",";
}


boolean allowImmediateCorrection = quiz.isImmediateCorrection();
String immediateCorrection = (allowImmediateCorrection)?"yes":"no";

boolean allowPracticeMode = quiz.isPracticeAllowed();
String allowPractice = (allowPracticeMode)?"yes":"no";

// Practice mode hasn't been done


%>

<form action="editQuizSettings" method="post">
<input type ="hidden" name = "old quiz title" value ="<%=title %>">
    Quiz Title: <input type="text" name="title" value = "<%= title%>"/> <br/>
    Quiz Description: <input type="text" name="description" value = "<%= description%>"/> <br/>

Random Order
					<select class="form-control" name="random order" > 
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>Current Setting: "<%= randomOrder %>"<br/>


Quiz Display
					<select class="form-control" name="quiz display">
						<option value="single">Single</option>
						<option value="multiple">Multiple</option>
					</select>Current Setting: "<%= quizDisplay %>"<br/>
					
Category
					<select class="form-control" name="category">
						<option value="General">General</option>
						<option value="Science">Science</option>
						<option value="Culture">Culture</option>
						<option value="Animals">Animals</option>
						<option value="History">History</option>
						<option value="Movies">Movies</option>
						<option value="Geography">Geography</option>
						<option value="Others">Others</option>
					</select>Current Setting: "<%= category %>"<br/>
					
Tags 
Current Setting: "<%= tag %>"<br/>
					<input type="text" name="tags" ><br/>

                   
					
Allow Immediate Correction
					<select class="form-control" name="immediate correction">
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>Current Setting: "<%= immediateCorrection %>"
					<br/>
					

Allow Practice
					<select class="form-control" name="allow practice">
						<option value="yes">Yes</option>
						<option value="no">No</option>
					</select>Current Setting: "<%= allowPractice %>"
					<br/>
					
					

    <input class = "btn btn-primary" type="submit" value="Edit"/>
    <a href = "quiz-editing.jsp">back</a>
    <%db.DBShutDown(); %>
</form>
</body>
</html>