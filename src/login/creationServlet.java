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

/**
 * Servlet implementation class creationServlet
 */
@WebServlet("/creationServlet")
public class creationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public creationServlet() {
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
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password_hash = convertToHash(password);
		String password2 = request.getParameter("password2");
		String password_hash2 = convertToHash(password);
		// String password_hash = password;

		DBInterface db = new DBInterface();

		if (password.equals(password2)) {
			
			if (password.isEmpty() || name.isEmpty()){
				RequestDispatcher rd = request.getRequestDispatcher("createFailed3.jsp");
				rd.forward(request, response);
				db.DBShutDown();
			}
			
			if (!db.isExistingUser(name)) {

			
			db.addNewUser(name, password_hash, false);
			 HttpSession session = request.getSession();
			 session.setAttribute("userName", name);
			 session.setAttribute("isAdmin", db.isAdministrator(name));

				RequestDispatcher rd = request.getRequestDispatcher("homepage.html");
				rd.forward(request, response);
				db.DBShutDown();
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("createFailed.jsp");
				rd.forward(request, response);
				db.DBShutDown();
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("createFailed2.jsp");
			rd.forward(request, response);
			db.DBShutDown();
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
