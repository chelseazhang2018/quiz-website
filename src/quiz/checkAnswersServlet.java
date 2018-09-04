package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 * Servlet implementation class checkAnswersServlet
 */
@WebServlet("/checkAnswersServlet")
public class checkAnswersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkAnswersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ArrayList<Question> questions =(ArrayList<Question>) session.getAttribute("questions");
		int numOfQuestions = questions.size();
		
		HashMap<String,ArrayList<Object>> checkAndRemove = (HashMap<String, ArrayList<Object>>) session.getAttribute("checkAndRemove");
		//System.out.println("****fucking testing*********");
		//System.out.println(checkAndRemove);
		
		int totalNumofQuestions = (int) session.getAttribute("totalNumofQuestions");
		//System.out.println("******************************");
		
		
		
		HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>) session.getAttribute("answersParams");
		HashMap<Integer,ArrayList<Object>> userAnswers = (HashMap<Integer, ArrayList<Object>>) session.getAttribute("answers");
		int total  = 0;
		for(int i = 0;i<answers.size();i++){
			String type = (String) answers.get(i).get(0);
			userAnswers.put(i, new ArrayList<Object>());
			userAnswers.get(i).add(type);
			//System.out.println("TYPE:"+answers.get(i).get(0));
			//System.out.println("*************");
			if(type.equals("question-response")||type.equals("fill-in-blank")||type.equals("multiple-choice")||type.equals("picture-response")){
				String ansName = (String)answers.get(i).get(1);
				String userAns = request.getParameter(ansName);
				userAnswers.get(i).add(userAns);
				int thisScore = (int)questions.get(i).calculateScore(userAns);
				if(checkAndRemove!=null){
					String key = questions.get(i).getDescription();
					//System.out.println("****fucking testing*********");
					//System.out.println(checkAndRemove);
					int timeCorrect  =(int) checkAndRemove.get(key).get(0);
					if(thisScore==questions.get(i).getTotalQuestions()){
						timeCorrect++;
						checkAndRemove.get(key).set(0,timeCorrect);
					}
					
				}
				
				total = total+thisScore;
				
				//System.out.println(total);
			}
			else{
				
				ArrayList<String> answerList  = new ArrayList<String>();
					for(int j = 1;j<answers.get(i).size();j++){
						
						//System.out.println("*******");
						String answerName = (String)answers.get(i).get(j);
						String ans = request.getParameter(answerName);
						if(ans!=null){
							answerList.add(ans);
						}
						
						userAnswers.get(i).add(request.getParameter(answerName));
						//System.out.println(answerName);
						//System.out.println("answer"+j+">>>>"+request.getParameter(answerName));
						}

					//System.out.println(answerList);
				int thisScore = (int)questions.get(i).calculateScore(answerList);
				if(checkAndRemove!=null){
					String key = questions.get(i).getDescription();
					int timeCorrect  =(int) checkAndRemove.get(key).get(0);
					if(thisScore==questions.get(i).getTotalQuestions()){
						timeCorrect++;
						checkAndRemove.get(key).set(0,timeCorrect);
					}
					
				}
					total = total+thisScore;
				
				//System.out.println(total);
			}
		}
		//System.out.println("***********&*^*^*^*^*^**********");
		if(checkAndRemove!=null){
			RequestDispatcher dispatch = request.getRequestDispatcher("pacticeModeServlet");
			dispatch.forward(request, response);
		}
		else{
			int score = (int) (((float)total/(float)totalNumofQuestions)*(float)100);
			//System.out.println(score+"");
			
			long timeEnded = java.lang.System.currentTimeMillis();
			long timeUsed = timeEnded-(long) session.getAttribute("timeStart");
			long second = (timeUsed/1000) % 60;
			long minute = (timeUsed/(1000*60)) % 60;
			long hour = (timeUsed/(1000*60*60)) % 24;
			String completeTime = String.format("%02d:%02d:%02d", hour, minute, second);
			request.setAttribute("userAnswers", userAnswers);
			request.setAttribute("score", score);
			request.setAttribute("corrretTotal", total);
			request.setAttribute("completeTime", completeTime);
			DBInterface db = new DBInterface();
			ArrayList<String> achievements = db.addNewActivity((String)session.getAttribute("userName"), (String)session.getAttribute("quizTitle"), score, timeUsed, (boolean)session.getAttribute("practiceMode"));	
			//System.out.println("achievement number is " + achievements.size());
			session.setAttribute("achievements", achievements);
			db.DBShutDown();
				RequestDispatcher dispatch = request.getRequestDispatcher("showResults.jsp");
				dispatch.forward(request, response);
			
//			if (!achievements.isEmpty()) {
//				session.setAttribute("achievements", achievements);
//				db.DBShutDown();
//				RequestDispatcher rd = request.getRequestDispatcher("display-achivement.jsp");
//				rd.forward(request, response);
//			}
//			else {
//				db.DBShutDown();
//				RequestDispatcher dispatch = request.getRequestDispatcher("showResults.jsp");
//				dispatch.forward(request, response);
//			}
			
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
