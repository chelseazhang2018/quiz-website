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
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<%
ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
HashMap<Integer,ArrayList<Object>> userAnswers = (HashMap<Integer, ArrayList<Object>>) session.getAttribute("answers");
%>
<body>
<h1> Congratulations! You've completed the quiz!</h1>
<p>Your score:<%=request.getAttribute("score")%></p>
<p>Your got <%=request.getAttribute("corrretTotal")%> out of <%=session.getAttribute("totalNumofQuestions")%> right</p>
<p>Your time used:<%=request.getAttribute("completeTime")%></p>

<%
ArrayList<String> achivements = (ArrayList<String>)session.getAttribute("achievements");
if (!achivements.isEmpty()){
%>

<div class = "achivement">
	You've Got The New Achievements:
	<%
	//(DBInterface)session.getAttribute("connection")
		//DBInterface db = new DBInterface();
	
	%>
	<ul class="list-group">
	<%
	for(int i = 0;i<achivements.size();i++){
	%>
	<li class="list-group-item"> <%=achivements.get(i) %> </li>
	<%
	}
}
	%>
	</ul>
</div>


<div class="compareAnswers">
    <div class = "userAnswer">
    your answer:
    <ul class="list-group">
        <%
        for(int i = 0;i<userAnswers.size();i++){
        	%>
        	<p><%=i+1%>.</p>
        	<%
        	String type = (String)userAnswers.get(i).get(0);
        	if(type.equals("question-response")||type.equals("fill-in-blank")||type.equals("multiple-choice")||type.equals("picture-response")){
				%>
				
				<li class="list-group-item"><%=userAnswers.get(i).get(1)%></li>
				<%
			}
			else{
				
					for(int j = 1;j<userAnswers.get(i).size();j++){
						if(userAnswers.get(i).get(j)!=null){
							
						
						%>
						<%=userAnswers.get(i).get(j)%>
						<%
					}
						}
			}
        	%>
        	<br>
        	<br>
        	<%
        }
        %>
    </ul>    
    </div>
    
    <div class = "correctAnswer">
     correct answer:
     <ul class="list-group">
        <%
        
        for(int i = 0;i<questions.size();i++){
        	Question q= questions.get(i);
        	%>
        	<li class="list-group-item"><%=i+1%>.</li>
        	<%
        	//System.out.println((String)userAnswers.get(i).get(0));
        	String type = (String)userAnswers.get(i).get(0);
        	//System.out.println(q.convertToDisplay(q.getAnswer()));
        	%>
        	<p><%=q.convertToDisplay(q.getAnswer()) %></p>
        	
        	<br>
        	<br>
        	<%
        }
        %>
       </ul> 
    </div>
</div>
</body>

<a href="index.jsp">back</a>
</html>