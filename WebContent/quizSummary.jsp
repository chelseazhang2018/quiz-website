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

<title>Insert title here</title>

<%

//(DBInterface)session.getAttribute("connection")



DBInterface db = new DBInterface();

String quizTitle = request.getParameter("quizTitle");

Quiz.QuizAbstract info= db.getQuizInfoByTitle(quizTitle);

String userName = (String)session.getAttribute("userName");





%>

<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>

<body>

<div class = "quizInfo">

<h1 class = "quizTitle">

<%=info.getTitle()%>
</h1>
<form action="reportQuiz" method="post">
 Reason<input type="text" name = "reason"/>
  <input type="hidden" value="<%=quizTitle %>" name = "quiz title"/>
    <input type="submit" value="Report Quiz"/>
</form>

<p class ="quizCreatInfo">

this quiz is contributed from 


<a href="friend-profile.jsp?submit=<%=info.getCreator()%>"><%=info.getCreator()%></a>



</p>

<p>

Quiz Description

</p>

<p>

<%=info.getDescription()%>

</p>

<p>

<span>Category:</span>

<span><%=info.getCategory()%></span>

</p>

<p>


<span>

Tags:<%=info.getTags() %>

</span>

<span>

<%-- <%=info.getTags()> --%>

</span>

</p>

<p class = "challengeFriends">

<a href = "message-challenge.jsp?quizTitle=<%=info.getTitle()%>">ChallengeMyFriends</a>

</p>

</div>

<form action="playQuizServlet" method = "get">

<%

if(info.isPracticeAllowed()){

%>

<span class = "practiceMode">

practiceMode

<input type="checkbox" name="practiceMode" value="yes">

</span>


<%

}

%>



<%System.out.println(info.getTitle());%>

<input type="hidden" name="quizTitle" value="<%=info.getTitle()%>">

<input class="btn btn-primary" type="submit" value="Start the Game!">

</form>

<%



if(((String)session.getAttribute("userName")).equals((String)info.getCreator())){

%>

<div class = "editQuiz">

<a class="btn btn-primary" href = "quiz-editing.jsp?quizTitle=<%=info.getTitle()%>">edit the quiz</a >

</div>

<%





}





%>

<div class = "quizHistory">


<%

String sortBy = request.getParameter("submit");

if (sortBy == null){

sortBy = "score";

}

ArrayList<Quiz.QuizTakingActivity> activities= db.getQuizTakingActivities(quizTitle);

ArrayList<Quiz.QuizTakingActivity> highestActivities = db.getListOfTopPerformersForQuiz(quizTitle,5);

ArrayList<Quiz.QuizTakingActivity> pastActivities= db.getUserQuizTakingActivitiesForQuiz(userName, quizTitle, sortBy, 5);




%>


<div class = "past performances"></div>

<h1> Your Past Performances</h1>

Sort By: 

<form action="quizSummary.jsp" method="post">

<input type="hidden" value="<%=quizTitle%>" name = "quizTitle"/>

    <input type="submit" name = "submit" value="completeTime"/>

    <input type="submit" name = "submit" value="score"/>

    <input type="submit" name = "submit" value="recordTime"/>

</form>

 

<table class="table">

<tr>

    <th>User Name</th>

    <th>Score</th>

    <th>Time used</th>
    
    <th>Date</th>

  </tr>

  <%for(int i = 0;i<pastActivities.size();i++){ 

%>

 

  <tr>

    <td>

<a href="friend-profile.jsp?submit=<%=pastActivities.get(i).getUsername()%>"><%=pastActivities.get(i).getUsername()%></a>

    </td>

    <td><%=pastActivities.get(i).getScore() %></td>

    <td><%=pastActivities.get(i).getCompleteTime()%></td>
    <td><%=pastActivities.get(i).getTakingTime()%></td>

</tr>

<%} %>

</table>

</div>


<div class = "Best Performances in the Last Day"></div>

<%


ArrayList<Quiz.QuizTakingActivity> lastDay = db.getListOfTopPerformersForPeriod(quizTitle, 24*60*60*1000);

%>

<h1> Best Performances in the Past Day</h1>

<table class="table">

<tr>

    <th>User Name</th>

    <th>Score</th>

    <th>Time used</th>

  </tr>

  <%for(int i = 0;i<lastDay.size();i++){ 

%>

 

  <tr>

    <td>

<a href="friend-profile.jsp?submit=<%=lastDay.get(i).getUsername()%>"><%=lastDay.get(i).getUsername()%></a>

    </td>

    <td><%=lastDay.get(i).getScore() %></td>

    <td><%=lastDay.get(i).getCompleteTime()%></td>

</tr>

<%} %>

</table>

</div>



<div class = "Quiz Statistics"></div>

<%

String summary = db.getQuizStatistics(quizTitle);

%>

<h1> Quiz Statistics</h1>

<br/>

<%= summary %><br/>






<div class = "highest performers"></div>

<h1> highest performers</h1>

<table class="table">

<tr>

    <th>User Name</th>

    <th>Score</th>

    <th>Time used</th>

  </tr>

  <%for(int i = 0;i<highestActivities.size();i++){ 

%>

 

  <tr>

    <td>

    <a href="friend-profile.jsp?submit=<%=highestActivities.get(i).getUsername()%>"><%=highestActivities.get(i).getUsername()%></a>

    </td>

    <td><%=highestActivities.get(i).getScore() %></td>

    <td><%=highestActivities.get(i).getCompleteTime()%></td>

</tr>

<%} %>

</table>

</div>

<div class = ""></div>

</body>

<%db.DBShutDown(); %>

<a href="quiz.jsp">back</a>

</html>