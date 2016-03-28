package ca.cafeteros.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.cafeteros.utilities.Logger;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log;

	public void init(ServletConfig config) throws ServletException {
		log = (Logger)config.getServletContext().getAttribute("Logger");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Executing login servlet url: " + request.getRequestURI());
		
		//response.sendRedirect(request.getContextPath() + "/home");
		response.sendRedirect((String)request.getSession().getAttribute("lastPage"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
