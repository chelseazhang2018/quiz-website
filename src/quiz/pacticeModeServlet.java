package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import question.*;
import database.*;
import support.*;
import support.Quiz.QuizAbstract;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class pacticeModeServlet
 */
@WebServlet("/pacticeModeServlet")
public class pacticeModeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public pacticeModeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
		if(questions.size()==0){
			RequestDispatcher dispatch = request.getRequestDispatcher("exitPractice.jsp");
			dispatch.forward(request, response);
		}
		else{

			HashMap<String,ArrayList<Object>> checkAndRemove = (HashMap<String, ArrayList<Object>>) session.getAttribute("checkAndRemove");
			//System.out.println("****fucking testing again*&*$#*********");
			//System.out.println(checkAndRemove);
			Iterator it = checkAndRemove.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				String key = (String) pair.getKey();
				if((int)checkAndRemove.get(key).get(0)>=3){
					int index = (int) checkAndRemove.get(key).get(1);
					questions.remove(index);
					//System.out.println("one question removed!");
				}
			}

			boolean immediateCorrect = (boolean) session.getAttribute("immediateCorrect");
			boolean onePage = (boolean) session.getAttribute("onePage");

			// determinin onePage vs MultiPages
			if(onePage){
				RequestDispatcher dispatch = request.getRequestDispatcher("onePage.jsp");
				dispatch.forward(request, response);
			} else{
				//immediateCorrect
				if(immediateCorrect){
					session.setAttribute("immediateCorrect",true);
				}
				else{
					session.setAttribute("immediateCorrect",false);
				}
				request.setAttribute("index", 0);
				String nextPage = questions.get(0).getType()+".jsp";

				RequestDispatcher dispatch = request.getRequestDispatcher("multiPage.jsp");
				dispatch.forward(request, response);
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
