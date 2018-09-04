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
 * Servlet implementation class homepageServlet
 */
@WebServlet("/homepageServlet")
public class homepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public homepageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		////System.out.println("haha");
		if (AutoLogin.hasDefaultInfo()) {
			System.out.println("hehe");
			String name = AutoLogin.getDefaultUsername();
			String password = AutoLogin.getDefaultPassword();
			//password = convertToHash(password);
			System.out.println(name);
			System.out.println(password);
			DBInterface db = new DBInterface();
			if (db.isValidUser(name, password)){	
				if (db.isBanned(name)) {
					RequestDispatcher rd = request.getRequestDispatcher("ban-house.jsp");
					db.DBShutDown();
					rd.forward(request,response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("userName", name);
					session.setAttribute("practiceMode", false);
					session.setAttribute("isAdmin", db.isAdministrator(name));
					RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
					db.DBShutDown();
					rd.forward(request,response);
				}
			} else {
				db.DBShutDown();
				RequestDispatcher rd = request.getRequestDispatcher("loginFailed.jsp");
				rd.forward(request,response);
			}
		}
		else {
			////System.out.println("hahe");
			RequestDispatcher rd = request.getRequestDispatcher("homepage.html");
			rd.forward(request,response);
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
