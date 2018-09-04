
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="java.util.HashMap"%>


<%@ page import="java.util.ArrayList"%>

<%
int i = Integer.parseInt(request.getParameter("index"));
Question question = ((ArrayList<Question>)session.getAttribute("questions")).get(i);
HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>)session.getAttribute("answersParams");
%>
	<h1><%=i+1%></h1>
	<p>this is a <%=question.getType() %> question</p>
	<p class = "description">
	<%=question.getDescription()%>
	</p>
	<% String indexOfAnwser="";
	if(!(boolean)session.getAttribute("quizEdit")){
		 indexOfAnwser = "answer"+i;
		answers.put(i,new ArrayList<Object>());
		answers.get(i).add(question.getType());
		answers.get(i).add(indexOfAnwser);
		System.out.println(indexOfAnwser);
	}
	%>
		<input id ="<%=indexOfAnwser%>" type="text" name ="<%=indexOfAnwser%>"></input>
		<input  type="hidden" name ="index" value="<%=i+""%>"></input>
	
	
	