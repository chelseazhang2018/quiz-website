
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="java.util.ArrayList"%>

<%
int i = Integer.parseInt(request.getParameter("index"));
MultiAnswer question =(MultiAnswer)((ArrayList<Question>)session.getAttribute("questions")).get(i);
int numOfAnswers = question.getNumOfAnswers();
HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>)session.getAttribute("answersParams");
%>
	<h1><%=i+1%></h1>
	<p>this is a <%=question.getType() %> question</p>
	<p> please insert @# between your answers</p>
	<p class = "description">
	<%=question.getQuery()%>
	</p>
	<% 
	if(!(boolean)session.getAttribute("quizEdit")){
	answers.put(i,new ArrayList<Object>());
	answers.get(i).add(question.getType());
	}
	%>
	<%
	for(int j = 0;j<numOfAnswers;j++){
		String indexOfAnwser = "multianswer"+i+j;
		if(!(boolean)session.getAttribute("quizEdit")){
		answers.get(i).add(indexOfAnwser);
		}
		%>
		<input class="form-control" id ="<%=indexOfAnwser%>" type="text" name ="<%=indexOfAnwser%>"></input>
		<%
	}
	%>
	<input type="hidden" name ="index" value="<%=i+""%>"></input>
	
	
	