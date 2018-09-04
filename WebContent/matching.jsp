
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="java.util.ArrayList"%>

<%
int i = Integer.parseInt(request.getParameter("index"));
Matching question =(Matching)((ArrayList<Question>)session.getAttribute("questions")).get(i);
HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>)session.getAttribute("answersParams");
%>
	<h1><%=i+1%></h1>
	<p>this is a <%=question.getType() %> question</p>
	<p class = "description">
	</p>
	<% String indexOfAnwser = "checkanswer"+i;
	
	System.out.println(indexOfAnwser);
	%>
	<%
	ArrayList<String> queryList = question.getQueryList();
	ArrayList<String> answerList = question.getAnswerList();
	if(!(boolean)session.getAttribute("quizEdit")){
	answers.put(i,new ArrayList<Object>());
	answers.get(i).add(question.getType());
	}
	for(int j = 0;j<queryList.size();j++){
		indexOfAnwser = "matchsanswer"+i+j;
		if(!(boolean)session.getAttribute("quizEdit")){
		answers.get(i).add(indexOfAnwser);
		}
		%>
	<span>
	<%=queryList.get(j) %>:
	<select name="<%=indexOfAnwser%>">
		<%
		
		for(int k =0;k<answerList.size();k++){
			%>
	<option id ="<%=indexOfAnwser%>" value="<%=answerList.get(k)%>"><%=answerList.get(k)%></option>
			
	<%
		}
	%>
	</select>
	</span>
	<br>
	<br>
	<%
	}
	%>
	<input type="hidden" name ="index" value="<%=i+""%>"></input>
	