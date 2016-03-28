package ca.cafeteros.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.cafeteros.entities.User;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.utilities.Mailer;

public class Signup extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String JSP_THANKYOU_PAGE = "/thankyou/signup";
	private static final String JSP_EMAIL = "/WEB-INF/view/emails/signupMail.jsp";
	private static final String EMAIL_SUBJECT = "Welcome to Cafeteros club web page. Please complete your registration";
    private Logger log;
    private User user; 
    private DB db;
    private Mailer mailer;
	
    public void init(ServletConfig config) throws ServletException{
    	log = (Logger)config.getServletContext().getAttribute("Logger");
    	db = (DB)config.getServletContext().getAttribute("db");
		mailer = (Mailer)config.getServletContext().getAttribute("mailer");
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	request.setAttribute("signupError", "active");
    	request.getRequestDispatcher("/WEB-INF/view/pages/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		log.info("Signing up a new user");
		
		String pass1 = request.getParameter("password");
		String pass2 = request.getParameter("password_confirm");
		String formName = request.getParameter("form_name");
		String agreement = request.getParameter("agreement");
		
		user = new User(log);
		user.setFName(request.getParameter("fname"));
		user.setLName(request.getParameter("lname"));
		user.setUsername(request.getParameter("email"));
		user.setEmail(request.getParameter("email"));
		
		if(user.validatePassword(pass1, pass2)){
			user.setPassword(org.apache.catalina.realm.RealmBase.Digest(pass1, "SHA-256", "UTF-8"));
		}
		
		if(agreement == null || !agreement.equals(new String("accepted"))){
			user.addError("agreement", "You have not acceted the agreement of the web site.");
		}
		
		try{
			User testEmail = db.getUserByEmail((String)request.getParameter("email"));
			
			if(testEmail.getEmail().equals(user.getEmail())){
				user.addError("email", "This email address already exists");
			}
		}catch(IOException ex){
			log.info("Email does not exist in the database. email: " + user.getEmail());
		}
		
		
		request.setAttribute("user", this.user);
		if(user.getErrors().isEmpty()){
			
			try{
				db.save(user);
				mailer.setTo(user.getEmail());
				mailer.setSubject(EMAIL_SUBJECT);
				CharArrayWriterResponse customResponse = new CharArrayWriterResponse(response);
				request.getRequestDispatcher(JSP_EMAIL).forward(request, customResponse);
				mailer.setContent(customResponse.getOutput());
				
				mailer.send();
				log.debug(request.getContextPath() + JSP_THANKYOU_PAGE);
				response.sendRedirect(request.getContextPath() + JSP_THANKYOU_PAGE);
				
			}catch (IOException ex){
				user.addError(formName, "An internal error has occured while regestring the user in the database. You were not able to signup in the website");
				log.error(ex.getMessage());
				doGet(request, response);
			}catch(RuntimeException ex){
	    		log.error(ex.getMessage());
	    	}
		}else{
			user.addError(formName, "Your user has not been registered. Please verify that the signup parameters are correct");
			doGet(request, response);
		}
	}
}
