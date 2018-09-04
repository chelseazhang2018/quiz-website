<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add a New Question</title>
<link rel="stylesheet" href="style/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"></head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<h1>Create New Question</h1>
<form action="createNewQuestionServlet" method="post">


Question Type
					<select class="form-control" name="question type">
						<option value="question-response">Question-Response</option>
						<option value="fill in the blank">Fill in the Blank</option>
						<option value="picture response question">Picture-Response Question</option>
						<option value="multi answer question">Multi-Answer Question</option>
						<option value="multiple choice">Multiple Choice</option>
						<option value="multiple choice with multiple answers">Multiple Choice with Multiple Answers</option>
						<option value="matching">Matching</option>
					</select><br/>
				
					    <input class="btn btn-info" type="submit" value="continue" name = "submit"/>
					    <input class="btn btn-success" type="submit" value="finish"  name = "submit"/>
</form>

</body>
</html>