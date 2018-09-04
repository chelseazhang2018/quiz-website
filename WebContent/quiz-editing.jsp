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
<title>Editing Quiz</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<% 
session = request.getSession();
DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
String quizTitle = request.getParameter("quizTitle");
if (quizTitle ==null){
	quizTitle = (String)session.getAttribute("quiz to be edited");
	
}

ArrayList<Question> questions = db.getQuestionsForQuiz(quizTitle);
session.setAttribute("quiz to be edited", quizTitle);
session.setAttribute("quizEdit", true);
System.out.println("quiz to be edit is"+ quizTitle);


%>
<form action="quiz-editing questions.jsp" method="post">
 
  <input class="form-control" type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input class = "btn btn-primary" type="submit" value="Edit Questions"/>
</form>


<form action="quiz-editing settings.jsp" method="post">
 
  <input class="form-control" type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input class = "btn btn-primary" type="submit" value="Edit Settings"/>
</form>

<form action="addNewQuestion" method="post">
 
  <input class="form-control" type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input class = "btn btn-primary" type="submit" value="Adding Questions"/>
</form>

<form action="quiz-editing remove.jsp" method="post">
 
  <input class="form-control" type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input class = "btn btn-primary" type="submit" value="Removing Questions"/>
</form>

<a href="quizSummary.jsp?quizTitle=<%=quizTitle%>">back</a>
<%db.DBShutDown(); %>
</body>
</html>