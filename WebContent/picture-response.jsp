<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="java.util.HashMap"%>


<%@ page import="java.util.ArrayList"%>

<%
Integer i = Integer.parseInt(request.getParameter("index"));
PictureResponse question = (PictureResponse)((ArrayList<Question>)session.getAttribute("questions")).get(i);
HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>)session.getAttribute("answersParams");
%>
	<h1><%=i+1%></h1>
	<p>this is a <%=question.getType() %> question</p>
	<p class = "description">
	<img src="<%=question.getDescription()%>"></img>
	
	</p>
	<%
	String indexOfAnwser="";
	if(!(boolean)session.getAttribute("quizEdit")){
	 indexOfAnwser = "answer"+i;
	System.out.println(indexOfAnwser);
	System.out.println(answers);
	System.out.println(indexOfAnwser);
	answers.put(i,new ArrayList<Object>());
	answers.get(i).add(question.getType());
	answers.get(i).add(indexOfAnwser);

	}

	%>
	<input class="form-control" id ="<%=indexOfAnwser%>" type="text" name ="<%=indexOfAnwser%>"></input>
	<input type="hidden" name ="index" value="<%=i+""%>"></input>
	
	