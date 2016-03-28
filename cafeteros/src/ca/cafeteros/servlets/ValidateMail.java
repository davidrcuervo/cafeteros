package ca.cafeteros.servlets;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.entities.User;
import ca.cafeteros.entities.UserRole;


public class ValidateMail extends HttpServlet {
	
	private static final String JSP_VALIDATE_MAIL = "/WEB-INF/view/pages/validateMail.jsp";
	private static final String JSP_THANKYOU_PAGE = "/thankyou/validateEmail";
	private static final long serialVersionUID = 1L;
	private Logger log;
	private DB db;
	private User user;
	
	public void init(ServletConfig config) throws ServletException{
		this.log = (Logger)config.getServletContext().getAttribute("Logger");
		this.db = (DB)config.getServletContext().getAttribute("db");
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Validating email and password for a new signup");
		
		String encodedEmail = (String)request.getParameter("user_email");
		String userEmail;
		if(encodedEmail == null || encodedEmail.isEmpty()){
			log.error("User email is not in the parameters of the URL.");
			response.sendRedirect(request.getContextPath() + "/error404");
		}else{
			userEmail = new String(Base64.getDecoder().decode(encodedEmail), "utf-8");
			request.setAttribute("userEmail", userEmail);
			request.getRequestDispatcher(JSP_VALIDATE_MAIL).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Validating email and password. email: " + request.getParameter("email"));
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email == null || email.isEmpty()){
			log.error("Email does not exist while validating password. This is not an editable field");
		}else{
			try{
				user = db.getUserByEmail(email);
				request.setAttribute("user", user);
				log.debug(Integer.toString(user.getUserRoles().size()));
				
				
				if(user.getUserRoles().size() > 0){
					log.error("The email " + user.getEmail() + " has been already validated");
					request.setAttribute("error_validateEmail", "Your email and password are already validated");
					doGet(request, response);
					
				}else if(!user.isPasswordMatch(org.apache.catalina.realm.RealmBase.Digest(password, "SHA-256", "UTF-8"))){
					request.setAttribute("error_validateEmail", "Your password does not match");
					doGet(request, response);
					
				}else{
					UserRole userRole = new UserRole();
					userRole.setUsername(user.getUsername());
					userRole.setRole("visitor");
					user.addRole(userRole);
					db.save(userRole);
					response.sendRedirect(request.getContextPath() + JSP_THANKYOU_PAGE);
				}
				
			}catch(IOException ex){
				log.error(ex.getMessage());
				request.setAttribute("error_validateEmail", "Your email is not registered, please try to signup again");
				doGet(request, response);
			}
		}
	}
}
