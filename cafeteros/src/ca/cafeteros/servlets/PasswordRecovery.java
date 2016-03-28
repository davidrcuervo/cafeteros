package ca.cafeteros.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.Mailer;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.entities.User;

public class PasswordRecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JSP_THANKYOU_PAGE = "/thankyou/passwordRecovery";
	private static final String JSP_EMAIL = "/WEB-INF/view/emails/passwordRecovery.jsp";
	private static final String EMAIL_SUBJECT = "Cafeteros club. Your password has been reseted";
	
	private Logger log;
	private DB db;
	private User user;
	private EntityManagerFactory emfactory;
	private Mailer mailer;

	public void init(ServletConfig config) throws ServletException {
		log = (Logger)config.getServletContext().getAttribute("Logger");
		db = (DB)config.getServletContext().getAttribute("db");
		emfactory = (EntityManagerFactory)config.getServletContext().getAttribute("EntityManagerFactory");
		mailer = (Mailer)config.getServletContext().getAttribute("mailer");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Executing doGet. doGet of this servlet is a mistake.");
		
		response.sendRedirect(request.getContextPath() + "/error404");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
log.info("recovering password for email: " + (String)request.getParameter("email"));
		
		String email = (String)request.getParameter("email");
		String randomPass = org.apache.commons.lang3.RandomStringUtils.random(9, true, true);
		String encryptedRandomPass = org.apache.catalina.realm.RealmBase.Digest(randomPass, "SHA-256", "UTF-8");
		log.debug("Random password is: " + randomPass);
		log.debug("Encrypted password is: " + encryptedRandomPass);
		
		try{
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			user = em.find(User.class, db.getUserByEmail(email).getId());
			user.setPassword(encryptedRandomPass);
			em.getTransaction().commit();
			em.close();
			
			mailer.setTo(user.getEmail());
			mailer.setSubject(EMAIL_SUBJECT);
			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(response);
			request.setAttribute("user", user);
			request.setAttribute("password", randomPass);
			request.getRequestDispatcher(JSP_EMAIL).forward(request, customResponse);
			mailer.setContent(customResponse.getOutput());
			
			mailer.send();
			response.sendRedirect(request.getContextPath() + JSP_THANKYOU_PAGE);
			
		}catch(Exception ex) {
			log.error("Password change failed while updating the database");
			log.error(ex.getMessage());
			
			request.setAttribute("forgetPasswordError", "Your email does not exist in this web site.");
			request.getRequestDispatcher("/WEB-INF/view/pages/login.jsp").forward(request, response);
		}
	}
}
