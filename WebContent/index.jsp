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
<title>homepage</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<% String userName = (String)session.getAttribute("userName"); %>


<%DBInterface db = new DBInterface();
	
	%>

<jsp:include page="navbar.jsp" flush="true"/>


	<!--Right side-->


<div class = "row">
<div class = "col-sm-6" class="container">
	<h2>Announcements:</h2>
	<%
	//(DBInterface)session.getAttribute("connection")
		//DBInterface db = new DBInterface();
	ArrayList<Announcement> announcements = db.getAnnouncements();
	%>
	<ul class="list-group">
	<%
	for(int i = 0;i<announcements.size();i++){
	%>
			 
	<li class="list-group-item"><%=announcements.get(i).getContent()%> by  	<a href="friend-profile.jsp?submit=<%=announcements.get(i).getAdministrator()%>"><%=announcements.get(i).getAdministrator()%></a>
	 at <%=announcements.get(i).getAnnounceTime()%> </li>
	<%
	}
	%>
	</ul>
</div>

<div class = "col-sm-6" class="container">
	Achievement:
	<%
	//(DBInterface)session.getAttribute("connection")
		//DBInterface db = new DBInterface();
	ArrayList<String> achivements = db.getUserAchievements(userName);
	%>
	<ul class="list-group">
	<%
	for(int i = 0;i<achivements.size();i++){
		if (!achivements.get(i).equals("")) {
	%>
		
		<li class="list-group-item"> <%=achivements.get(i) %> </li>
	<%
		}
	}
	%>
	</ul>
</div>
</div>


<div class = "row">
<div class = "col-sm-6" class="container">
Here are some popularQuizzes:
	<ul class="list-group">
	<%
	ArrayList<Quiz.QuizAbstract> popularQuizzes = db.getPopularQuizzes(5);
	for(int i = 0;i<popularQuizzes.size();i++){ 
		%>
		<li class="list-group-item"><a href ="quizSummary.jsp?quizTitle=<%=popularQuizzes.get(i).getTitle()%>"><%=popularQuizzes.get(i).getTitle()%></a></li>
		<%
	}
	%>
	
	</ul>
</div>

<div  class = "col-sm-6" class="container" id ="recently created quizzes">
Here are some recently created quizzes:
	<ul class="list-group">
	<%
	ArrayList<Quiz.QuizAbstract> recentQuizzes = db.getMostRecentlyCreatedQuizzes(5);
	for(int i = 0;i<recentQuizzes.size();i++){ 
		%>
		<li class="list-group-item"><a href ="quizSummary.jsp?quizTitle=<%=recentQuizzes.get(i).getTitle()%>"><%=recentQuizzes.get(i).getTitle()%></a></li>
		<%
	}
	%>
		
	</ul>
</div>
</div>




	<%
	
	 ArrayList<Quiz.QuizTakingActivity> recentTakenQuizzes = db.getUserQuizTakingActivities(userName);
	%>

<div class = "row">
<div class = "col-sm-6" class="container">

Here are your recently taken quizzes:
	 	<%if (!recentTakenQuizzes.isEmpty()) {%>
		 	<table class="table">
		 		<tr>
			    <th>Quiz</th>
			    <th>Score</th>		
			    <th>Time used</th>
			  </tr>
			  <%for(int i = 0;i<recentTakenQuizzes.size();i++){ 
				%>
			 
			 
			  <tr>
			    <td><%=recentTakenQuizzes.get(i).getTitle()%></td>
			    <td><%=recentTakenQuizzes.get(i).getScore() %></td>		
			    <td><%=recentTakenQuizzes.get(i).getCompleteTime()%></td>
			 </tr>
			 
			 <%}
			  %>
			  </table>
			  <%
			 }
			 else {%>
			 <br/>
			
			  <ul><li>You Haven't Taken Any Quizzes Recently</li></ul>
			 <%} %>
		 	
</div>


<div class = "col-sm-6" class="container" id ="user recent activities">

	<% 
	ArrayList<Quiz.QuizAbstract> recentCreatedQuizzes = db.getUserQuizCreatingActivities(userName);
	if(recentCreatedQuizzes.size()>0){
		%>	Here are your recently created quizzes:
		 	<table class="table">
		 		<tr>
			    <th>Quiz</th>
			    <th>TimeCreated</th>		
			  </tr>
			  <%for(int i = 0;i<recentCreatedQuizzes.size();i++){ 
				%>
			 
			 
			  <tr>
			    <td><a href = "quizSummary.jsp?quizTitle=<%=recentCreatedQuizzes.get(i).getTitle()%>"><%=recentCreatedQuizzes.get(i).getTitle()%></a></td>
			    <td><%=recentCreatedQuizzes.get(i).getCreateTime() %></td>		
			 </tr>
			 <%} %>
		 	</table>
</div>	
</div>

		<%
	}
	%>
<div class="container" class = "activity">
	Friends Activities:<br/>
	<%
	ArrayList<String> friends = db.getFriend(userName);
	for (int i = 0; i < friends.size(); i++){
		String friendName = friends.get(i);
		ArrayList<Quiz.QuizTakingActivity> activities = db.getUserQuizTakingActivities(friendName);
		String nameToPrint = friendName;
		%>
		<a href="friend-profile.jsp?submit=<%=nameToPrint%>"><%=nameToPrint%></a> <ul>
		<% 
		for (int j = 0; j < 3 && j < activities.size(); j++){
			String title = activities.get(j).getTitle();
			float score = activities.get(j).getScore();	
			String completeTime = activities.get(j).getCompleteTime();
			%>
			<ul class="list-group">
			<li class="list-group-item">Took Quiz 
			<a href= "quizSummary.jsp?quizTitle=<%=title%>"> <%=title %></a> and get <%= score %> at <%=completeTime %>.
			</li>
			</ul>
		
			
			<% 
		}
		%>
		<%if (activities.isEmpty())
			{%>
			No activity
			<% }%>
		</ul>
		<% 
	}
	
	if (friends.isEmpty()){%>
	<ul class="list-group"><li class="list-group-item">
	No activity</li></ul>
	<%
		
	}
	%>

</div>
<%
db.DBShutDown();
%>
<form action="logoutServlet" method="post">
    <input type="submit" value="logout"/>
</form>
</body>
</html>