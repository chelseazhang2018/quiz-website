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
<title>play</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="navbar.jsp" flush="true"/>
	
	<div class="container"  class = "allQuiz">
	<h2> Here are all quizzes:</h2>
		<%
		//(DBInterface)session.getAttribute("connection")
		DBInterface db = new DBInterface();
		ArrayList<Quiz.QuizAbstract> allQuizzes = db.getListOfQuizInfo();
		
		Map<String,List<Quiz.QuizAbstract>>categQuizzes  = new HashMap<String,List<Quiz.QuizAbstract>>();
		%>
		<ul class="list-group">
			<%
			categQuizzes.put("Others",new ArrayList<Quiz.QuizAbstract>());
		   for (int i = 0; i < allQuizzes.size(); i++) {
			   String category = allQuizzes.get(i).getCategory();
			   if(category.equals("")){
				   categQuizzes.get("Others").add(allQuizzes.get(i));
			   }
			   else{
				   if(!categQuizzes.containsKey(category)){
					   categQuizzes.put(category,new ArrayList<Quiz.QuizAbstract>());
					   categQuizzes.get(category).add(allQuizzes.get(i));
				   }
				   else{
					   categQuizzes.get(category).add(allQuizzes.get(i));
				   }
			   }
			   
			%>
			<li class="list-group-item"><a href="quizSummary.jsp?quizTitle=<%=allQuizzes.get(i).getTitle()%>">
			<%=allQuizzes.get(i).getTitle()%>
			</a></li>
			<% } %>
		</ul>
	</div>
	<div class="container"  class = "categoriedQuiz">
	<h2> Here are categorized quizzes:</h2>
			<%
			Iterator it = categQuizzes.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		    %>
		       
		    <div class="container"><%=pair.getKey() %>
		    <ul class="list-group">
		    <%
		    List<Quiz.QuizAbstract>aSetOfQuizzes =(ArrayList<Quiz.QuizAbstract>)pair.getValue();
		    for(int j = 0;j<aSetOfQuizzes.size();j++){
		    	
		    %>
		    <li class="list-group-item"><a href="quizSummary.jsp?quizTitle=<%=aSetOfQuizzes.get(j).getTitle()%>">
			<%=aSetOfQuizzes.get(j).getTitle()%>
			</a></li>
		    <%	
		    }
		    %>
		    </ul>
		    </div>
		    <%
	        it.remove(); // avoids a ConcurrentModificationException
		    }
			%>
	</div>
	
<%
db.DBShutDown();
%>

<form action="search-quiz.jsp" method="post">
Keyword: <input type="text" name= "name"></input>
    <input type="submit" value="Search Quiz"/>
</form>

<a href="index.jsp">back</a>
</body>
</html>