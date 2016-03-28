package ca.cafeteros.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.entities.User;
import com.google.gson.Gson;


public class Ajax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log;
	private DB db;
	private String[] pathParts;
	private String json;
	private Map<String, String> jsonMap;

	public void init(ServletConfig config) throws ServletException {
		db = (DB)config.getServletContext().getAttribute("db");
		log = (Logger)config.getServletContext().getAttribute("Logger");
		jsonMap = new LinkedHashMap<String, String>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("executing doGet of Ajax servlet");
		
		String path = request.getPathInfo();
		Map<String, String> test = new LinkedHashMap<String, String>();
		
		test.put("path", path);
		//json = new Gson().toJson(test);
		validateLoginEmail((String)request.getParameter("email"));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Executing doPost of Ajax servlet");
		
		pathParts = request.getPathInfo().split("/");
		
		if(pathParts.length > 1){
		
			switch(pathParts[1]){
				case "loginEmail":
					String email = request.getParameter("email");
					validateLoginEmail(email);
					
					break;
				
				default: 
					break;	
			}
		
		}else{
			log.error("The url has more parameters that required");
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	private void validateLoginEmail(String email){
		log.info("Validation if email exists and has been confirmed. email: " + email);
		
		if(email  == null || email.isEmpty()){
			log.info("Post request does not include any email info.");
			jsonMap.put("status", "error");
			jsonMap.put("error", "Please, Make sure that you have typed a valid email");
		}else{
			try
			{
				User user = db.getUserByEmail(email);
				log.debug("user roles: " + Integer.toString(user.getUserRoles().size()));
				if(user.getUserRoles().size() > 0){
					jsonMap.put("status", "success");
					jsonMap.put("error", "");
				}else{
					jsonMap.put("status", "error");
					jsonMap.put("error", "The email has not been validated yet. Please check your email inbox and confirm your password by following the instructions sent in a previus message");
				}
				
			}catch (IOException ex){
				jsonMap.put("status", "error");
				jsonMap.put("error", "Your email does not exist in our website please signup before trying to login.");
			}
		}
		json = new Gson().toJson(jsonMap);
	}

}
