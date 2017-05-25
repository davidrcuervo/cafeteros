package ca.cafeteros.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laetienda.logger.JavaLogger;

public class Root extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private String[] pathParts;
	private JavaLogger log;
	
	public void init(ServletConfig config) throws ServletException {
		
	}
	
	private void build(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pathParts = (String[])request.getAttribute("pathParts");
		log = (JavaLogger)request.getAttribute("logger");
		log.info("executing root servlet");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		build(request, response);
		
		switch(pathParts[0]){
			
			case(""):
				response.sendRedirect("/2017");
				break;
			
			case("verano-2017"):
				request.getRequestDispatcher("/WEB-INF/jsp/folletos/verano-2017.jsp").forward(request, response);
				break;
		
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		build(request, response);
	}
}