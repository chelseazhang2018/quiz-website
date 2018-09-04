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

import database.DBInterface;
import question.Question;
import support.Quiz;

/**
 * Servlet implementation class createNewQuestionServlet
 */
@WebServlet("/createNewQuestionServlet")
public class createNewQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createNewQuestionServlet() {
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
		Quiz quiz = (Quiz) session.getAttribute("newQuiz");
		ArrayList<Question> questions = quiz.getQuestions();
		System.out.print("question number is "+questions.size());


		String questionType = request.getParameter("question type");

		String submitType = request.getParameter("submit");
		// System.out.println(submitType);
		if (submitType.equals("continue")) {
			switch (questionType) {
			case "question-response": {
				//System.out.println(questionType + "createNewQuestion");
				session.setAttribute("questionType", "question-response");
				RequestDispatcher rd = request.getRequestDispatcher("create-question-response.jsp");
				rd.forward(request, response);
			}
			break;

			case "fill in the blank": {
				//System.out.println(questionType + "createNewQuestion");

				session.setAttribute("questionType", "fill in the blank");
				RequestDispatcher rd = request.getRequestDispatcher("create-fill in the blank.jsp");
				rd.forward(request, response);
			}
			break;

			case "picture response question": {
				//System.out.println(questionType + "createNewQuestion");


				session.setAttribute("questionType", "picture response question");
				RequestDispatcher rd = request.getRequestDispatcher("create-picture response.jsp");
				rd.forward(request, response);
			}
			break;

			case "multi answer question": {
				//System.out.println(questionType + "createNewQuestion");


				session.setAttribute("questionType", "multi answer question");
				RequestDispatcher rd = request.getRequestDispatcher("create-multi-answer question.jsp");
				rd.forward(request, response);
			}
			break;

			case "multiple choice": {
				//System.out.println(questionType + "createNewQuestion");


				session.setAttribute("questionType", "multiple choice");
				RequestDispatcher rd = request.getRequestDispatcher("create-multiple choice.jsp");
				rd.forward(request, response);
			}
			break;

			case "multiple choice with multiple answers": {
				//System.out.println(questionType + "createNewQuestion");


				session.setAttribute("questionType", "multiple choice with multiple answers");
				RequestDispatcher rd = request.getRequestDispatcher("create-multiple choice with multiple answers.jsp");
				rd.forward(request, response);
			}
			break;

			case "matching": {
				//System.out.println(questionType + "createNewQuestion");


				session.setAttribute("questionType", "matching");
				RequestDispatcher rd = request.getRequestDispatcher("create-matching.jsp");
				rd.forward(request, response);
			}
			break;

			}
		} else {
			boolean editMode = (boolean) session.getAttribute("edit mode");
			if (editMode){
				DBInterface db = new DBInterface(); 
				String creator = (String) session.getAttribute("userName");
				String oldQuizTitle = (String) session.getAttribute("quiz to be edited");
				//System.out.println("User is "+creator);



				boolean test = db.editQuizQuestion(creator, oldQuizTitle, quiz);
				if (test == false)
					//System.out.println("add fail");
				session.setAttribute("edit mode", false);
				//System.out.println("finish adding");
				//	db.addNewQuiz(quiz, creator);
				db.DBShutDown();

				RequestDispatcher rd = request.getRequestDispatcher("quiz-editing.jsp");
				rd.forward(request, response);
			}
			else{
				//System.out.println("create");
				String creator = (String) session.getAttribute("userName");
				DBInterface db = new DBInterface();
				ArrayList<String> achievements = db.addNewQuiz(quiz, creator);
				if (!achievements.isEmpty()) {
					session.setAttribute("achievements", achievements);

					db.DBShutDown();
					RequestDispatcher rd = request.getRequestDispatcher("display-achivement.jsp");
					rd.forward(request, response);
				} else {
					db.DBShutDown();
					RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
					rd.forward(request, response);
				}
			}
		}
	}
}
