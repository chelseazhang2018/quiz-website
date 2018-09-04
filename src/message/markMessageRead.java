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
 * Servlet implementation class markMessageRead
 */
@WebServlet("/markMessageRead")
public class markMessageRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public markMessageRead() {
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
		String userName = (String) session.getAttribute("userName");
		int messagePosition = Integer.parseInt(request.getParameter("message position"));
		DBInterface db = new DBInterface();
		ArrayList<Message> messages = db.getAllMessages(userName);
		db.markMessageRead(messages.get(messagePosition));
		db.DBShutDown();
		RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
        rd.forward(request,response);
		
	}

}
