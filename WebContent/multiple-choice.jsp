
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="java.util.ArrayList"%>

<%
int i = Integer.parseInt(request.getParameter("index"));
MultipleChoice question =(MultipleChoice)((ArrayList<Question>)session.getAttribute("questions")).get(i);
HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>)session.getAttribute("answersParams");
%>
	<h1><%=i+1%></h1>
	<p>this is a <%=question.getType() %> question</p>
	<p class = "description">
	<%=question.getQuery()%>
	
	</p>
	<% 
	
	String indexOfAnwser = "answer"+i;
	if(!(boolean)session.getAttribute("quizEdit")){
	answers.put(i,new ArrayList<Object>());
	answers.get(i).add(question.getType());
	answers.get(i).add(indexOfAnwser);
	}
	System.out.println(indexOfAnwser);
	%>
	<%
	ArrayList<String> options = question.getOptions();
	for(int j = 0;j<options.size();j++){
	%>
	<input class="form-control" id ="<%=indexOfAnwser%>" type="radio" name="<%=indexOfAnwser%>" value="<%=options.get(j)%>"><%=options.get(j)%><br>
	<%
	}
	%>
	<input  type="hidden" name ="index" value="<%=i+""%>"></input>
	