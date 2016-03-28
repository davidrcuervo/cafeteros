package ca.cafeteros.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.cafeteros.utilities.Logger;


public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log;
	
	public void init(ServletConfig config) throws ServletException{
    	log = (Logger)config.getServletContext().getAttribute("Logger");
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Executing logut servlet: URI: " + request.getRequestURI());
		
		try{
			request.getSession().removeAttribute("sessionUser");
			request.getSession().invalidate();
			request.setAttribute("logoutMessage", "User has been logout succesfully");
		}catch (NullPointerException ex){
			log.info(ex.getMessage());
		}finally{
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home"));
		}
	}
}
