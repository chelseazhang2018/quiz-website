<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="database.DBInterface"%>
        <%@ page import="javax.servlet.*"%>
        <%@ page import="java.util.*"%>
        <%@ page import="support.Quiz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Clear Quiz History</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
Clear Quiz History


<% DBInterface db = new DBInterface();
String userName = (String)session.getAttribute("userName");
ArrayList<Quiz.QuizAbstract> quizs = db.getListOfQuizInfo();
%>
        <% 
for (int i = 0; i < quizs.size(); i++){
	String nameToPrint = quizs.get(i).getTitle();
	
	%>

	<form action="clearQuizHistory" method="post">
	<%=nameToPrint %>
	<input class="form-control" type="hidden" value="<%=nameToPrint %>" name = "quiz name"/>
    <input class="btn btn-warning" type="submit" value="Clear"/>
</form>
	<br/>
	<%} %>
	<br/>
	<a href="administration.jsp">back</a>
	<%db.DBShutDown(); %>
 
</body>
</html>