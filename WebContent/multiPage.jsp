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
</head>
<%
int i = 0;
if(request.getAttribute("index")!=null){
	 i =(int)request.getAttribute("index");
}
else{
	 i =Integer.parseInt(request.getParameter("index"));
}
ArrayList<Question> questions =(ArrayList<Question>) session.getAttribute("questions");
String nextPage = questions.get(i).getType();
nextPage = nextPage+".jsp";
System.out.println(nextPage);
%>

<body>
<%
//allow immediate correction

%>

<form action ="multipageServlet" method = "GET" ">
	<jsp:include page="<%=nextPage%>" flush="true" >
		<jsp:param name="index" value="<%=i%>"/>
	</jsp:include>
	<input type="hidden" value="<%=i%>"></input>
	<input type="submit" value="submit"></input>
</form>
<div class = "exitPractice">

<%if((boolean)session.getAttribute("practiceMode")){
%>

<a href ="practiceModeExitServlet">exit practice mode</a >
<%
}
%>



</div>


</body>
</html>