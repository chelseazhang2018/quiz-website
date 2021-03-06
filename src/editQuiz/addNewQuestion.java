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
import question.Question;
import support.Quiz;

/**
 * Servlet implementation class addNewQuestion
 */
@WebServlet("/addNewQuestion")
public class addNewQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addNewQuestion() {
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
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		DBInterface db = new DBInterface();
		String quizTitle = request.getParameter("quiz title");
		String questionNumber = request.getParameter("question number");
		ArrayList<Question> questions = db.getQuestionsForQuiz(quizTitle);
		
	//	String questionType = questions.get(Integer.parseInt(questionNumber)).getType();

		
		
		
		HttpSession session = request.getSession();
		session.setAttribute("questions", questions);
		session.setAttribute("question number", questionNumber);
		session.setAttribute("quiz to be edited", quizTitle);
	
		
		Quiz.QuizAbstract abQuiz = db.getQuizInfoByTitle(quizTitle);
		boolean random = abQuiz.isRandom();
		boolean onePage = abQuiz.isOnePage();
		boolean practiceMode = abQuiz.isPracticeAllowed(); //???
		boolean immediateCorrection = abQuiz.isImmediateCorrection();
		String title = abQuiz.getTitle();
		String category = abQuiz.getCategory();
		String description = abQuiz.getDescription();
		boolean allowPractice = abQuiz.isPracticeAllowed();
		ArrayList<String> tags = abQuiz.getTags();
		 Quiz quizToBeEdited=  new Quiz( random,  onePage,  practiceMode,  immediateCorrection,  title,  category,  description,  allowPractice,  questions, tags);
		 request.getSession().setAttribute("newQuiz", quizToBeEdited);
			request.getSession().setAttribute("questionType", new String());
			boolean editMode = true;
session.setAttribute("edit mode", editMode);
db.DBShutDown();
			RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
	        rd.forward(request,response);
	}

}
