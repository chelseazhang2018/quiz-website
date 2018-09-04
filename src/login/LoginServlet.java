package login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DBInterface;
import support.AutoLogin;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		 String name = request.getParameter("name");
		 String password = request.getParameter("password");
		 password = convertToHash(password);
	     String rememberMe_string = request.getParameter("remember me");
	     boolean rememberMe = (rememberMe_string != null);
		 if (rememberMe) {
			 AutoLogin.saveDefaultUser(name, password);
		 }
		 DBInterface db = new DBInterface();
		 
		 HttpSession session = request.getSession();
		 session.setAttribute("userName", name);
		 
		 session.setAttribute("practiceMode", false);
		 session.setAttribute("isAdmin", db.isAdministrator(name));
		
		 if (db.isValidUser(name, password)){
			RequestDispatcher rd;
			if (db.isBanned(name)) {
				rd = request.getRequestDispatcher("ban-house.jsp");
			} else {
				rd = request.getRequestDispatcher("index.jsp");
			}
			db.DBShutDown();
			rd.forward(request,response);
	           
		 }
		 else {
			 db.DBShutDown();
			 RequestDispatcher rd = request.getRequestDispatcher("loginFailed.jsp");
	            rd.forward(request,response);
	           
			 
		 }
		 
		 
	}
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff; // remove higher bits, sign
			if (val < 16)
				buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	private String convertToHash(String word) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA");
			message.update(word.getBytes());
			return hexToString(message.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}





