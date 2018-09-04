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
<title>Friend Profile</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<% 
DBInterface db = new DBInterface();
//String friendName = (String)session.getAttribute("friend name"); 
String friendName = request.getParameter("submit");
   String myName = (String)session.getAttribute("userName");
   if (!db.isFriend(myName, friendName) && !myName.equals(friendName)){%>
   
   <form action="addNewFriend" method="post">
<input class="form-control" type="hidden" name= "friend name" value = "<%=friendName%>"></input>
    <input class="btn btn-success" type="submit" value="Send Friend Request"/>
</form>
   <%    
   }
%>
Welcome to <%= friendName %>'s Page!

<form action="reportUser" method="post">
 Reason<input type="text" name = "reason"/>
  <input type="hidden" value="<%=friendName %>" name = "friend name"/>
    <input type="submit" value="Report User"/>
</form>

<div class = "achivement">
	<%= friendName %> Achievement:
	<%
	//(DBInterface)session.getAttribute("connection")
		//DBInterface db = new DBInterface();
	ArrayList<String> achivements = db.getUserAchievements(friendName);
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

<div class = "activity">
	
	<%
	
		ArrayList<Quiz.QuizTakingActivity> activities = db.getUserQuizTakingActivities(friendName);
		%>
		<%=friendName %> Activities
		<ul class="list-group">
		<% 
		for (int j = 0; j < 3 && j < activities.size(); j++){
			String title = activities.get(j).getTitle();
			float score = activities.get(j).getScore();	
			String completeTime = activities.get(j).getCompleteTime();
			%>
			<li class="list-group-item">Took <%= title %> and get <%= score %> at <%=completeTime %>.</li>
			
			
			<% 
		}
		%>
		<%if (activities.size() == 0)
			{%>
			No activity
			<% }%>
		</ul>


</div>
<%
db.DBShutDown();
%>
<a href="index.jsp">back</a>
</body>
</html>