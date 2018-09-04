package createNewQuiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Servlet implementation class newQuestionCreated
 */
@WebServlet("/newQuestionCreated")
public class newQuestionCreated extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public newQuestionCreated() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		HttpSession session = request.getSession();
		String questionType = (String) session.getAttribute("questionType");
		Quiz newQuiz = (Quiz) session.getAttribute("newQuiz");

		////System.out.println(questionType+"test");
		switch (questionType) {
		case "question-response": {
			//System.out.println(questionType + "QuestionCreated");
			String query = request.getParameter("query");
			String answer = request.getParameter("answer");
			Question newQuestion = new QuestionResponse(query, answer);
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());

			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
			rd.forward(request, response);
		}
			break;

		case "fill in the blank": {
			//System.out.println(questionType + "QuestionCreated");
			String preQuery = request.getParameter("pre-query");
			String postQuery = request.getParameter("post-query");
			String answer = request.getParameter("answer");
			if (preQuery == null)
				preQuery = "";
			if (postQuery == null)
				postQuery = "";
			if (answer == null)
				answer = "";
			
			Question newQuestion = new FillInBlank(preQuery, postQuery, answer);
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "picture response question": {
			//System.out.println(questionType + "QuestionCreated");
			String imageLocation = request.getParameter("image location");
			String answer = request.getParameter("answer");
	
			Question newQuestion = new PictureResponse(imageLocation, answer);
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
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
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
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
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
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
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
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
			newQuiz.addQuestion(newQuestion);
			session.setAttribute(questionType, new String());
			
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
			rd.forward(request, response);
		}
			break;

		}

	}

}
