package createNewQuiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBInterface;
import question.Question;
import support.Quiz;

/**
 * Servlet implementation class createNewQuizServlet
 */

@WebServlet("/createNewQuizServlet")
public class createNewQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createNewQuizServlet() {
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
		String random_string = request.getParameter("random order");
		boolean random = (random_string.equals("yes")) ? true : false;
		
		String onePage_string = request.getParameter("quiz display");
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
		String[] tagList = tags_string.split(",");
		
		ArrayList<String> tags = new ArrayList<String>();
		for (int i = 0; i < tagList.length; i++) {
			tags.add(tagList[i].trim());
		}

		
		ArrayList<Question> questions = new ArrayList<Question>();
		
		
		
		
		
		request.getSession().setAttribute("newQuiz", new Quiz(random, onePage, practiceMode, immediateCorrection,  title, category,  description, allowPractice, questions, tags));
		
		//request.getSession().setAttribute("newQuiz", new Quiz(random, onePage, practiceMode, immediateCorrection,  title, category,  description));
		request.getSession().setAttribute("questionType", new String());
		request.getSession().setAttribute("edit mode", false);
		DBInterface db = new DBInterface();
		if (db.getQuizInfoByTitle(title) != null){
			db.DBShutDown();
			RequestDispatcher rd = request.getRequestDispatcher("createQuizFailed.jsp");
	        rd.forward(request,response);
		}
		
db.DBShutDown();
		RequestDispatcher rd = request.getRequestDispatcher("question-create.jsp");
        rd.forward(request,response);
	}

}
