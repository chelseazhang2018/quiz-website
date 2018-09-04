<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="java.util.*"%>
<%@ page import="database.*"%>
<%@ page import="question.*"%>
<%@ page import="support.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Fill in the Blank</title>
</head>


<body>
<form action="quizEdited" method="post">
    Pre-query: <input class="form-control" type="text" name="pre-query"/> <br/>
    Post-query: <input class="form-control" type="text" name="post-query"/> <br/>
    Answer: <input class="form-control" type="text" name="answer"/> <br/>
    <input class="btn btn-primary" type="submit" value="create"/>
    
</form>
</body>
</html>