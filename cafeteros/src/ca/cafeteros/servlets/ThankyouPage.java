package ca.cafeteros.servlets;

import java.io.IOException;
import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.cafeteros.utilities.Logger;

public class ThankyouPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JSP_THANKYOU = "/WEB-INF/view/thankyouPage/";
    
	private Logger log;
	private String[] pathParts;
	
	public void init(ServletConfig config) throws ServletException {
		this.log = (Logger)config.getServletContext().getAttribute("Logger");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Running doGet method for thank you page");
		
		pathParts = request.getPathInfo().split("/");
		
		File jspFile = new File(request.getServletContext().getRealPath(JSP_THANKYOU + "thankyou_" + pathParts[1] + ".jsp"));
		
		if(!jspFile.exists() || !jspFile.isFile()){
			log.error("URL path does not match any available option. path info: " + pathParts[1]);
			response.sendRedirect(request.getContextPath() + "/error404");
		} else{
			
			request.getRequestDispatcher(JSP_THANKYOU + "thankyou_" + pathParts[1] + ".jsp").forward(request, response);;
		}
	}
}
