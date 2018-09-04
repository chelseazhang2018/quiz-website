<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Muilti-Answer Question</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<% 
String test = (String)request.getParameter("answer number");
int choiceNumber = Integer.parseInt(test);
%>

<form action="newQuestionCreated" method="post">

<input type="hidden" name="answer number" value= <%=test %>>

    Query: <input class="form-control" type="text" name="query"/> <br/>
    <% 
for (int i = 0; i < choiceNumber; i++){
	%>
	Answer<%=i+1%>: <% String name = "answer" + (i + 1); %>
	<input class="form-control" type="text" name= "<%= name %>"/> 
	<%System.out.println("Name is " + name); %>
	<br/>
	<%} %>
	<br/>

Answer Has Order<select class="form-control" name="has order">
						<option value="yes">Yes </option>
						<option value="no">No </option>
						</select>
						<br/>
						
    <input class="btn btn-primary" type="submit" value="create"/>
    
    
</form>
</body>
</html>