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
int i = (int)request.getAttribute("index");
int immediateScore = (int)request.getAttribute("immediateScore");
int totalNumofQuestions = (int) session.getAttribute("totalNumofQuestions");
immediateScore = (int) (((float)immediateScore/(float)totalNumofQuestions)*(float)100);
%>
<body>
<div class="compareAnswers">
		<div class = "userAnswer">
    your answer is:
        	<%
        	String type = (String)userAnswers.get(i).get(0);
        	if(type.equals("question-response")||type.equals("fill-in-blank")||type.equals("multiple-choice")||type.equals("picture-response")){
				%>
				
				<p><%=userAnswers.get(i).get(1)%></p>
				<%
			}
			else{
				
					for(int j = 1;j<userAnswers.get(i).size();j++){
						%>
						<%=userAnswers.get(i).get(j)%>
						<%
						}
			}
        	%>
        	<br>
        	<%
        %>
    </div>
    
     <div class = "correctAnswer">
     correct answer:
        <%
        	%>
       
        	<%
        	//System.out.println((String)userAnswers.get(i).get(0));
        	Question q= questions.get(i);
        	%>	
			<p><%=q.convertToDisplay(q.getAnswer()) %></p>
			<br/>
    </div>
</div>
<div>

you got <%=request.getAttribute("immediateScore")%> points for this question!
<form action ="multiPage.jsp" method = "GET" ">
	<input type = "hidden" name="index" value="<%=i+1%>"></input>
	<input type="submit" value="continue"></input>
</form>

</div>

</body>
</html>