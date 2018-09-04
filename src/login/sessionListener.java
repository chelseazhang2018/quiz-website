package login;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import support.Quiz;

/**
 * Application Lifecycle Listener implementation class sessionListener
 *
 */
@WebListener
public class sessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public sessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
//    	//System.out.println("Initialize done");
//    	arg0.getSession().setAttribute("newQuiz", new Quiz(false, false, false, false,  "title", "category",  "description"));
//    	if (arg0.getSession().getAttribute("newQuiz") == null)
//    		//System.out.println("null2");
//    	arg0.getSession().setAttribute("questionType", new String());
//    	if (arg0.getSession().getAttribute("newQuiz") == null)
//    		//System.out.println("null3");
    	
    	
    	
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
