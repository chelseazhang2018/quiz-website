package administration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DBInterface;
import support.Announcement;

/**
 * Servlet implementation class createNewAnnouncement
 */
@WebServlet("/createNewAnnouncement")
public class createNewAnnouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createNewAnnouncement() {
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
		HttpSession session = request.getSession();
		String administrator = (String) session.getAttribute("userName");
		
		String content = request.getParameter("content");
		
		//String announceTime = "";
		//Announcement newAnnouncement = new Announcement(content, administrator, announceTime);
		DBInterface db = new DBInterface();
		db.addNewAnnouncement(content, administrator);
		db.DBShutDown();
		 RequestDispatcher rd = request.getRequestDispatcher("admin-create announcement.jsp");
         rd.forward(request,response);
		
		
		
	}

}
