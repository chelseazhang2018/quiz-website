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
<%System.out.println("11111111");%>
<% ArrayList<Question> questions =(ArrayList<Question>) session.getAttribute("questions"); %>
<body>
	<div class = "playQuizzesInOnePage">
	<form action ="checkAnswersServlet" method = "GET">
	<%
	System.out.println(questions.size());
		for(int i = 0; i<questions.size();i++){
			String type = questions.get(i).getType();
			System.out.println(type);
			String myVariable = type+".jsp";
			System.out.println(myVariable);
			%>
<jsp:include page="<%=myVariable %>" flush="true" >
	<jsp:param name="index" value="<%=i%>"/>
</jsp:include>
		<%
		}
		%>
	
	<input class="btn btn-success" type="submit" value = "submit"></input>
	</form>
	</div>
<div class = "exitPractice">
<%if((boolean)session.getAttribute("practiceMode")){
%>

<a class="btn btn-info" href ="practiceModeExitServlet">exit practice mode</a >
<%
}
%>






</div>
</body>
</html>