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
<title>Editing Questions</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>



<% 
DBInterface db = new DBInterface();
session = request.getSession();
String userName = (String)session.getAttribute("userName");

String quizTitle = request.getParameter("quiz title");

if (quizTitle ==null){
	quizTitle = (String)session.getAttribute("quiz to be edited");
	
}


ArrayList<Question> questions = db.getQuestionsForQuiz(quizTitle);
session.setAttribute("questions", questions);
//System.out.println("questions:" + questions);

%>
<body>
Editing Quiz
	<div class = "playQuizzesInOnePage">

	<%
	
	//System.out.println(questions.size());
		for(int i = 0; i<questions.size();i++){
			String type = questions.get(i).getType();
			String myVariable = type+".jsp";
			System.out.println(myVariable);
			%>
			
<jsp:include page="<%=myVariable%>" flush="true" >
	<jsp:param name="index" value="<%=i%>"/>
</jsp:include>


<form action="editQuizQuestions" method="post">
 <input class="form-control" type="hidden" value="<%=i%>" name = "question number"/>
  <input class="form-control" type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input class="btn btn-info" type="submit" value="Edit" name ="submit"/>
    <input class="btn btn-success" type="submit" value="Finish" name ="submit"/>
</form>
		<%
		}
		%>
	


<%db.DBShutDown(); %>

<a href = "quiz-editing.jsp">back</a>
</div>
</body>
</html>