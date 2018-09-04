<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
 <%@ page import="javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Guest Homepage</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>

<h1>Welcome </h1>






	<%DBInterface db = new DBInterface();
	 session = request.getSession();
	String userName = (String) session.getAttribute("userName");

	
	
	%>

	<!--Right side-->

<div class = "annoucements">
	Announcements:
	<%
	ArrayList<Announcement> announcements = db.getAnnouncements();
	%>
	<ul>
	<%
	for(int i = 0;i<announcements.size();i++){
	%>
	<li><%=announcements.get(i).getContent()%> by  <%=announcements.get(i).getAdministrator()%> at <%=announcements.get(i).getAnnounceTime()%> </li>
	<%
	}
	%>
	</ul>
</div>


<div class = "popularQuizzes">
Here are some popularQuizzes:
	<ul>
	<%
	ArrayList<Quiz.QuizAbstract> popularQuizzes = db.getPopularQuizzes(5);
	for(int i = 0;i<popularQuizzes.size();i++){ 
		%>
		<li><a href ="quizSummary.jsp?quizTitle=<%=popularQuizzes.get(i).getTitle()%>"><%=popularQuizzes.get(i).getTitle()%></a></li>
		<%
	}
	%>
	
	</ul>
</div>

<div class = "popularQuizzes">
All Quizzes We Have:
	<ul>
	<%
	ArrayList<Quiz.QuizAbstract> quizzes = db.getListOfQuizInfo();
	for(int i = 0;i<quizzes.size();i++){ 
		String quizName = quizzes.get(i).getTitle();
		String description = quizzes.get(i).getDescription();
		%>
	<li>	<%= quizName %> <br/></li>
		<%="\t" %>Description: <%= description %><br/><br/>
			<%
	}
	%>
	
	</ul>
</div>






<%
db.DBShutDown();
%>
<form action="logoutServlet" method="post">
 
  
    <input type="submit" value="logout"/>
</form>
</body>
</html>