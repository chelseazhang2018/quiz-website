package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DBInterface;
import question.Question;

/**
 * Servlet implementation class multipageServlet
 */
@WebServlet("/multipageServlet")
public class multipageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public multipageServlet() {
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
		int index = Integer.parseInt(request.getParameter("index"));
		int corrretTotal = (int) session.getAttribute("correct");
		int numOfQuestions = questions.size();
		int total  = 0;
		
		//System.out.println("******************************");
		
		HashMap<Integer,ArrayList<Object>> answers = (HashMap<Integer,ArrayList<Object>>) session.getAttribute("answersParams");
		HashMap<Integer,ArrayList<Object>> userAnswers = (HashMap<Integer, ArrayList<Object>>) session.getAttribute("answers");
		HashMap<String,ArrayList<Object>> checkAndRemove = (HashMap<String, ArrayList<Object>>) session.getAttribute("checkAndRemove");
		
			String type = (String) answers.get(index).get(0);
			userAnswers.put(index, new ArrayList<Object>());
			userAnswers.get(index).add(type);
			//System.out.println("TYPE:"+answers.get(index).get(0));
			boolean immediateFeedback = true;
			//System.out.println("*************");
			if(type.equals("question-response")||type.equals("fill-in-blank")||type.equals("multiple-choice")||type.equals("picture-response")){
				String ansName = (String)answers.get(index).get(1);
				String userAns = request.getParameter(ansName);
				userAnswers.get(index).add(userAns);
				int thisScore = (int)questions.get(index).calculateScore(userAns);
				if(checkAndRemove!=null){
					String key = questions.get(index).getDescription();
					//System.out.println("****fucking testing*********");
					//System.out.println(checkAndRemove);
					int timeCorrect  =(int) checkAndRemove.get(key).get(0);
					if(thisScore==questions.get(index).getTotalQuestions()){
						timeCorrect++;
						checkAndRemove.get(key).set(0,timeCorrect);
					}
					
				}
				
				total = total+thisScore;
				
				//System.out.println(total);
			}
			else{
				ArrayList<String> answerList  = new ArrayList<String>();
					for(int j = 1;j<answers.get(index).size();j++){
						
						//System.out.println("*******");
						String answerName = (String)answers.get(index).get(j);
						userAnswers.get(index).add(request.getParameter(answerName));
						String ans = request.getParameter(answerName);
						if(ans!=null){
							answerList.add(ans);
						}
						
						//System.out.println(answerName);
						//System.out.println("answer"+j+">>>>"+request.getParameter(answerName));
						}
					
					int thisScore = (int)questions.get(index).calculateScore(answerList);
					if(checkAndRemove!=null){
						String key = questions.get(index).getDescription();
						int timeCorrect  =(int) checkAndRemove.get(key).get(0);
						if(thisScore==questions.get(index).getTotalQuestions()){
							timeCorrect++;
							checkAndRemove.get(key).set(0,timeCorrect);
						}
						
					}
					total = total+thisScore;
				
				//System.out.println(total);
			}
			
		
			corrretTotal += total; 
			session.setAttribute("correct",corrretTotal);
			
		if(index<numOfQuestions-1){
			if((boolean) session.getAttribute("immediateCorrect")){
				request.setAttribute("index", index);
				request.setAttribute("immediateScore",total);
				RequestDispatcher dispatch = request.getRequestDispatcher("imdediateCheck.jsp");
				dispatch.forward(request, response);
				
			}
			request.setAttribute("index", index+1);
			String nextPage = questions.get(0).getType()+".jsp";

			RequestDispatcher dispatch = request.getRequestDispatcher("multiPage.jsp");
			dispatch.forward(request, response);
		
			
		} else{
			if(checkAndRemove!=null){
				RequestDispatcher dispatch = request.getRequestDispatcher("pacticeModeServlet");
				dispatch.forward(request, response);
			}
			else{
				int totalNumofQuestions = (int) session.getAttribute("totalNumofQuestions");
				
				long timeEnded = java.lang.System.currentTimeMillis();
				long timeUsed = timeEnded-(long) session.getAttribute("timeStart");
				long second = (timeUsed/1000) % 60;
				long minute = (timeUsed/(1000*60)) % 60;
				long hour = (timeUsed/(1000*60*60)) % 24;
				
				String completeTime = String.format("%02d:%02d:%02d", hour, minute, second);
				int score = (int) (((float)corrretTotal/(float)totalNumofQuestions)*(float)100);
				request.setAttribute("score", score);
				request.setAttribute("corrretTotal", corrretTotal);
				request.setAttribute("completeTime", completeTime);
				request.setAttribute("userAnswers", userAnswers);
				DBInterface db = new DBInterface();
				ArrayList<String> achievements = db.addNewActivity((String)session.getAttribute("userName"), (String)session.getAttribute("quizTitle"), score, timeUsed, (boolean)session.getAttribute("practiceMode"));	

					session.setAttribute("achievements", achievements);
				db.DBShutDown();
					RequestDispatcher dispatch = request.getRequestDispatcher("showResults.jsp");
					dispatch.forward(request, response);
				
				
				
//				//System.out.println("_____________++++++++++");
//				RequestDispatcher dispatch = request.getRequestDispatcher("showResults.jsp");
//				//System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
//				//System.out.println(dispatch);
//				dispatch.forward(request, response);
			}
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
