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
 * Servlet implementation class editQuizSettings
 */
@WebServlet("/editQuizSettings")
public class editQuizSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editQuizSettings() {
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
		String random_string = request.getParameter("random order");
		boolean random = (random_string.equals("yes")) ? true : false;
		
		String onePage_string = request.getParameter("quiz display");
		//System.out.println("One page is" + onePage_string);
		boolean onePage = (onePage_string.equals("single"))?true:false;
		
		String practiceMode_string = request.getParameter("allow practice");
		boolean practiceMode = (practiceMode_string.equals("yes"))?true:false;
		
		String immediateCorrection_string = request.getParameter("immediate correction");
		boolean immediateCorrection = (immediateCorrection_string.equals("yes"))?true:false;
		
		
		String allowPractice_string = request.getParameter("allow practice");
		boolean allowPractice = (allowPractice_string.equals("yes"))?true:false;
	
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		
		String tags_string = request.getParameter("tags");
		//System.out.println("^^^^^" +tags_string);
		//if (tags)
		String[] tagList = tags_string.split(",");
		
		ArrayList<String> tags = new ArrayList<String>();
		for (int i = 0; i < tagList.length; i++) {
			tags.add(tagList[i].trim());
		}
		
		
		DBInterface db = new DBInterface();
		
		String oldTitle = request.getParameter("old quiz title");
		Quiz.QuizAbstract quiz = db.getQuizInfoByTitle(oldTitle);
		String createTime = quiz.getCreateTime();
		String creator = quiz.getCreator();
		//ArrayList<Question> questions = db.getQuestionsForQuiz(oldTitle);
		
		
		
		Quiz.QuizAbstract editedQuiz= new Quiz.QuizAbstract(title, category, description, createTime, creator, tags, allowPractice, onePage, random, immediateCorrection);
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("userName");
		
			
			//Quiz editedQuiz = new Quiz(random, onePage, practiceMode, immediateCorrection,  title, category,  description, allowPractice, questions, tags);
		
		boolean test =	db.editQuizAbstract(username, oldTitle, editedQuiz);
		//System.out.println("old tiltil is " +oldTitle);
		if (test == false)
			//System.out.println("edit setting fali");
		db.DBShutDown();
		RequestDispatcher rd = request.getRequestDispatcher("quiz-editing settings.jsp");
        rd.forward(request,response);
	}

}
