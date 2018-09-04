package editQuiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DBInterface;
import question.FillInBlank;
import question.Matching;
import question.MultiAnswer;
import question.MultipleChoice;
import question.MultipleChoiceWithMultipleAnswer;
import question.PictureResponse;
import question.Question;
import question.QuestionResponse;
import support.Quiz;

/**
 * Servlet implementation class quizEdited
 */
@WebServlet("/quizEdited")
public class quizEdited extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public quizEdited() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String questionType = (String) session.getAttribute("questionType");
		Quiz newQuiz = (Quiz) session.getAttribute("newQuiz");
		ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
        int questionNumber = Integer.parseInt((String) session.getAttribute("question number"));
        String quizTitle = (String) session.getAttribute("quiz to be edited");
		DBInterface db = new DBInterface();
		String username = (String) session.getAttribute("userName");
		
		////System.out.println(questionType+"test");
		switch (questionType) {
		case "question-response": {
			
			//System.out.println(questionType + "Edit");
			String query = request.getParameter("query");
			String answer = request.getParameter("answer");
			Question newQuestion = new QuestionResponse(query, answer);
			//System.out.println("%%%%% Answer is "+newQuestion.getAnswer());
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			
			//newQuiz.addQuestion(newQuestion);
			newQuiz.editQuestions(newQuestions);
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			session.setAttribute("questions", newQuestions);

			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;

		case "fill in the blank": {
			//System.out.println(questionType + "QuestionCreated");
			String preQuery = request.getParameter("pre-query");
			String postQuery = request.getParameter("post-query");
			String answer = request.getParameter("answer");
			
			Question newQuestion = new FillInBlank(preQuery, postQuery, answer);
			
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			//newQuiz.addQuestion(newQuestion);
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "picture response question": {
			//System.out.println(questionType + "QuestionCreated");
			String imageLocation = request.getParameter("image location");
			String answer = request.getParameter("answer");
	
			Question newQuestion = new PictureResponse(imageLocation, answer);
		//	newQuiz.addQuestion(newQuestion);
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			
			
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "multi answer question": {
			//System.out.println(questionType + "QuestionCreated");
			String answerNumber = request.getParameter("answer number");
			String query = request.getParameter("query");
			String hasOrder_string = request.getParameter("has order");
			boolean hasOrder = (hasOrder_string.equals("yes"))?true:false;
			System.out.print(hasOrder);
			
			ArrayList<String> answerList = new ArrayList<String>();
			for (int i = 0; i < Integer.parseInt(answerNumber); i++){
				String name = "answer" + (i + 1);
				String answer = request.getParameter(name);
				//System.out.println("enter loop" + answer);
				answerList.add(answer);
			}
			Question newQuestion = new MultiAnswer(query, answerList, hasOrder);
		//	newQuiz.addQuestion(newQuestion);
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;
			
			
		case "multiple choice": {
			//System.out.println(questionType + "QuestionCreated");
		
			String optionNumber = request.getParameter("option number");
			
			String query = request.getParameter("query");
			String answer = request.getParameter("answer");
			
			
			ArrayList<String> options = new ArrayList<String>();
			
			// Create options
			for (int i = 0; i < Integer.parseInt(optionNumber); i++){
				String name = "option" + (i + 1);
				String option = request.getParameter(name);
				//System.out.println("enter loop" + option);
				options.add(option);
			}
			
			Question newQuestion = new MultipleChoice(query, answer, options);
		//	newQuiz.addQuestion(newQuestion);
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "multiple choice with multiple answers": {
			//System.out.println(questionType + "QuestionCreated");
			String answerNumber = request.getParameter("answer number");
			String optionNumber = request.getParameter("option number");
			
			String query = request.getParameter("query");
			
			
			ArrayList<String> answerList = new ArrayList<String>();
			ArrayList<String> options = new ArrayList<String>();
			
			// Create answerlist
			for (int i = 0; i < Integer.parseInt(answerNumber); i++){
				String name = "answer" + (i + 1);
				String answer = request.getParameter(name);
				//System.out.println("enter loop" + answer);
				answerList.add(answer);
			}
			
			// Create options
			for (int i = 0; i < Integer.parseInt(optionNumber); i++){
				String name = "option" + (i + 1);
				String option = request.getParameter(name);
				//System.out.println("enter loop" + option);
				options.add(option);
			}
			
			Question newQuestion = new MultipleChoiceWithMultipleAnswer(query, answerList, options);
		//	newQuiz.addQuestion(newQuestion);
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;
			
			
		case "matching": {
			//System.out.println(questionType + "QuestionCreated");
			String pairNumber = request.getParameter("pair number");
			
			
			ArrayList<String> answerList = new ArrayList<String>();
			ArrayList<String> queryList = new ArrayList<String>();
			
			// Create answerlist
			for (int i = 0; i < Integer.parseInt(pairNumber); i++){
				String name = "answer" + (i + 1);
				String answer = request.getParameter(name);
				//System.out.println("enter loop" + answer);
				answerList.add(answer);
			}
			
			// Create queryList
			for (int i = 0; i < Integer.parseInt(pairNumber); i++){
				String name = "query" + (i + 1);
				String query = request.getParameter(name);
				//System.out.println("enter loop" + query);
				queryList.add(query);
			}
			
			Question newQuestion = new Matching(queryList, answerList);
		//	newQuiz.addQuestion(newQuestion);
			ArrayList<Question> newQuestions = questions;
			newQuestions.set(questionNumber, newQuestion);
			
			db.editQuizQuestion(username, quizTitle, newQuiz);
			db.DBShutDown();
			session.setAttribute(questionType, new String());
			//db.DBShutDown();
			RequestDispatcher rd = request.getRequestDispatcher("quiz-editing questions.jsp");
			rd.forward(request, response);
		}
			break;

		}
	}

}
