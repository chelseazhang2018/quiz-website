package message;

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
import support.Message;

/**
 * Servlet implementation class friendRequest
 */
@WebServlet("/friendRequest")
public class friendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public friendRequest() {
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
		String action = request.getParameter("action");
		if (action.equals("Accept")){
			
			HttpSession session = request.getSession();
			String myName = (String) session.getAttribute("userName");
			String senderName = request.getParameter("sender name");
			
			String messagePosition_string = request.getParameter("message position");
			int messagePostion = Integer.parseInt(messagePosition_string);

			DBInterface db = new DBInterface();
			ArrayList<Message> messages = db.getAllMessages(myName);
			Message message = messages.get(messagePostion);
			db.markMessageRead(message);			
			db.addFriend(myName, senderName);
			db.DBShutDown();
			 RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
	         rd.forward(request,response);
		}
		else {
			HttpSession session = request.getSession();
			String myName = (String) session.getAttribute("userName");
			String senderName = request.getParameter("sender name");
			String messagePosition_string = request.getParameter("message postion");
			int messagePostion = Integer.parseInt(messagePosition_string);

			DBInterface db = new DBInterface();
			ArrayList<Message> messages = db.getAllMessages(myName);
			Message message = messages.get(messagePostion);
			db.markMessageRead(message);			
			//db.addFriend(myName, senderName);
			db.DBShutDown();
			 RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
	         rd.forward(request,response);
		}
	}

}
