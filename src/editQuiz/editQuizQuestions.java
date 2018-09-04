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
 * Servlet implementation class editQuizQuestions
 */
@WebServlet("/editQuizQuestions")
public class editQuizQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editQuizQuestions() {
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
	//	doGet(request, response);
		DBInterface db = new DBInterface();
		String quizTitle = request.getParameter("quiz title");
		String questionNumber = request.getParameter("question number");
		ArrayList<Question> questions = db.getQuestionsForQuiz(quizTitle);
		
		String questionType = questions.get(Integer.parseInt(questionNumber)).getType();
		questionType = convertQuestionType(questionType);
		//System.out.println("question type is "+ questionType);
		
		
		
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
			
		
		 String submitType = request.getParameter("submit");
			if (submitType.equals("Edit")) {
		
		switch (questionType) {
		case "question-response": {
			//System.out.println(questionType + "createNewQuestion");
			session.setAttribute("questionType", "question-response");
			RequestDispatcher rd = request.getRequestDispatcher("edit-question-response.jsp");
			rd.forward(request, response);
		}
			break;

		case "fill in the blank": {
			//System.out.println(questionType + "createNewQuestion");
			
			session.setAttribute("questionType", "fill in the blank");
			RequestDispatcher rd = request.getRequestDispatcher("edit-fill in the blank.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "picture response question": {
			//System.out.println(questionType + "createNewQuestion");
			
			
			session.setAttribute("questionType", "picture response question");
			RequestDispatcher rd = request.getRequestDispatcher("edit-picture response.jsp");
			rd.forward(request, response);
		}
			break;

		case "multi answer question": {
			//System.out.println(questionType + "createNewQuestion");
			
			
			session.setAttribute("questionType", "multi answer question");
			RequestDispatcher rd = request.getRequestDispatcher("edit-multi-answer question.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "multiple choice": {
			//System.out.println(questionType + "createNewQuestion");
			
			
			session.setAttribute("questionType", "multiple choice");
			RequestDispatcher rd = request.getRequestDispatcher("edit-multiple choice.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "multiple choice with multiple answers": {
			//System.out.println(questionType + "createNewQuestion");
			
			
			session.setAttribute("questionType", "multiple choice with multiple answers");
			RequestDispatcher rd = request.getRequestDispatcher("edit-multiple choice with multiple answers.jsp");
			rd.forward(request, response);
		}
			break;
			
		case "matching": {
			//System.out.println(questionType + "createNewQuestion");
			
			
			session.setAttribute("questionType", "matching");
			RequestDispatcher rd = request.getRequestDispatcher("edit-matching.jsp");
			rd.forward(request, response);
		}
			break;

		}
			}
			else{
				String creator = (String) session.getAttribute("userName");
				String oldQuizTitle = (String) session.getAttribute("quiz to be edited");
				
				Quiz quiz = (Quiz) session.getAttribute("newQuiz");
				db.editQuizQuestion(creator, oldQuizTitle, quiz);
				//System.out.println("finish Editing");
			//	db.addNewQuiz(quiz, creator);
				db.DBShutDown();
				RequestDispatcher rd = request.getRequestDispatcher("quiz-editing.jsp");
				rd.forward(request, response);
			}
		
	}
	
private String convertQuestionType(String a){
	
	if (a.equals("multiple-choice"))
		return "multiple choice";
	
	if (a.equals("question-response"))
		return "question-response";
	
	if (a.equals("matching"))
		return "matching";
	
	if (a.equals("fill-in-blank"))
		return "fill in the blank";
	
	
	if (a.equals("picture-response"))
		return "picture response question";
	
	if (a.equals("multi-answer"))
		return "multi answer question";
	
	if (a.equals("multiple-choice-with-multiple-answer"))
		return "multiple choice with multiple answers";
	
	return "";
	
}

}
