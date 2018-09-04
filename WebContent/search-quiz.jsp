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
<title>Insert title here</title>
</head>
<body>
	<%String quizName = request.getParameter("name");
DBInterface db = new DBInterface();
ArrayList<Quiz.QuizAbstract > quizs = db.searchQuiz(quizName);

if (quizs.isEmpty()){
	%>
	No Result Found
	<%
}
else {
%>

	<% 

for (int i = 0; i < quizs.size(); i++){
	String nameToPrint = quizs.get(i).getTitle();
	String myName = (String)session.getAttribute("userName");

	%>
 
    <a href ="quizSummary.jsp?quizTitle=<%=nameToPrint%>"><%=nameToPrint%></a>


	<br />
	<%}} %>
	<br />
	<a href="index.jsp">back</a>

</body>
</html>