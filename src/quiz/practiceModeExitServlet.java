package quiz;

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

/**
 * Servlet implementation class practiceModeExitServlet
 */
@WebServlet("/practiceModeExitServlet")
public class practiceModeExitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public practiceModeExitServlet() {
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
		HttpSession session = request.getSession();
	
		if ((boolean) session.getAttribute("practiceMode")) {

			DBInterface db = new DBInterface();
			ArrayList<String> achievements = db.addNewActivity((String) session.getAttribute("userName"), "", 0, 0,
					true);
			System.out.print("Multi-page achievements number is " + achievements.size());
			if (!achievements.isEmpty()) {
				session.setAttribute("achievements", achievements);
				db.DBShutDown();
				session.setAttribute("practiceMode", false);
				RequestDispatcher dispatch = request.getRequestDispatcher("display-achivement.jsp");
				dispatch.forward(request, response);

			} else {
				String quizTitle = (String) session.getAttribute("quizTitle");
				String link = "quizSummary.jsp?quizTitle=" + quizTitle;
				db.DBShutDown();
				// <a href
				// ="quizSummary.jsp?quizTitle=<%=(String)session.getAttribute("quizTitle")%>">exit
				// practice mode</a >
				session.setAttribute("practiceMode", false);
				RequestDispatcher dispatch = request.getRequestDispatcher(link);
				dispatch.forward(request, response);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}
