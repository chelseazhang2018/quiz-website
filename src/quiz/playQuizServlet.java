package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import question.*;
import database.*;
import support.*;
import support.Quiz.QuizAbstract;


/**
 * Servlet implementation class playQuizServlet
 */
@WebServlet("/playQuizServlet")
public class playQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public playQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String quizTitle = request.getParameter("quizTitle");
		//System.out.println(quizTitle);
		String practiceMode = "no";
		//(DBInterface)session.getAttribute("connection")
		
			DBInterface db = new DBInterface();
			HttpSession session = request.getSession();
			session.setAttribute("quizEdit",false);
			
			ArrayList<Question> questions = db.getQuestionsForQuiz(quizTitle);
			int totalNumofQuestions = 0;
			for(int i = 0;i<questions.size();i++){
				totalNumofQuestions += questions.get(i).getTotalQuestions();
			}
			
			Quiz.QuizAbstract quizInfo = db.getQuizInfoByTitle(quizTitle);
		
			boolean immediateCorrect = quizInfo.isImmediateCorrection();
			boolean onePage = quizInfo.isOnePage();
			boolean random = quizInfo.isRandom();
			practiceMode = request.getParameter("practiceMode");
			HashMap<Integer,ArrayList<Object>> userAnswers = new HashMap<Integer,ArrayList<Object>>();
			HashMap<Integer,ArrayList<Object>> userAnswersParams = new HashMap<Integer,ArrayList<Object>>();
			//System.out.println(practiceMode);
			session.setAttribute("immediateCorrect", immediateCorrect);
			
			session.setAttribute("onePage", onePage);
			if(random){
				Collections.shuffle(questions);
			}
			session.setAttribute("questions", questions);
			
			session.setAttribute("answers", userAnswers);
			session.setAttribute("answersParams", userAnswersParams);
			long timeStarted = java.lang.System.currentTimeMillis();
			session.setAttribute("timeStart",timeStarted);
			session.setAttribute("correct", 0);
			session.setAttribute("quizTitle", quizInfo.getTitle());
			Integer num = 0;
			session.setAttribute("totalNumofQuestions",totalNumofQuestions);
			db.DBShutDown();
			//System.out.println(practiceMode+"   "+"fucking testing");
		if(practiceMode==null||!practiceMode.equals("yes")){
			// determinin onePage vs MultiPages
			if(onePage){
				RequestDispatcher dispatch = request.getRequestDispatcher("onePage.jsp");
				dispatch.forward(request, response);
			}
			else{
				//immediateCorrect
				if(immediateCorrect){
					session.setAttribute("immediateCorrect",true);
				}
				else{
					session.setAttribute("immediateCorrect",false);
				}
				request.setAttribute("index", 0);
				//String nextPage = questions.get(0).getType()+".jsp";
				
				RequestDispatcher dispatch = request.getRequestDispatcher("multiPage.jsp");
				dispatch.forward(request, response);
			}
		}
		
		else{
			HashMap<String,ArrayList<Object>> checkAndRemove = new HashMap<String,ArrayList<Object>>();
			for(int j  =0;j<questions.size();j++){
				checkAndRemove.put(((Question)questions.get(j)).getDescription(),new ArrayList<Object>());
				checkAndRemove.get(((Question)questions.get(j)).getDescription()).add(0);
				checkAndRemove.get(((Question)questions.get(j)).getDescription()).add(j);
			}
			session.setAttribute("checkAndRemove", checkAndRemove);
			session.setAttribute("practiceMode", true);
			
			////System.out.println(checkAndRemove);
			RequestDispatcher dispatch = request.getRequestDispatcher("pacticeModeServlet");
			dispatch.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
