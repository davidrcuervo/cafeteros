package ca.cafeteros.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import ca.cafeteros.utilities.Logger;

public class Login implements Filter {
	
	private Logger log;

    public Login() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Running filter Login");
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		
		String path = httpReq.getRequestURI();
		String ctxPath = httpReq.getContextPath();
		
		path = path.substring(path.indexOf(ctxPath) + ctxPath.length());
		
		String[] pathParts = path.split("/");
		
		log.debug("context path: " + httpReq.getContextPath());
		log.debug("path: " + path);
		log.debug("first subpath: " + pathParts[1]);
		
		if(pathParts[1].equals("ajax") || pathParts[1].equals("assets")){
			//DO NOTHING
			log.debug("doing nothing in filter");
		} else {
			log.debug("filter will procede to save lastPage URI");
			
			String currentPage;
			String lastPage;
			
			String email = (String)httpReq.getRemoteUser();
					
			if(email == null || email.isEmpty()){
				
			}else{
				ca.cafeteros.beans.SessionUser sessionUser = new ca.cafeteros.beans.SessionUser();
				sessionUser.setRequest(httpReq);
				httpReq.setAttribute("sessionUser", sessionUser);
			}
			
			if(httpReq.getSession().getAttribute("currentPage") != null){
				lastPage = (String)httpReq.getSession().getAttribute("currentPage");
				httpReq.getSession().removeAttribute("lastPage");
				httpReq.getSession().setAttribute("lastPage", lastPage);
				httpReq.getSession().removeAttribute("currentPage");
			}
			
			currentPage = httpReq.getRequestURI();
			httpReq.getSession().setAttribute("currentPage", currentPage);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log = (Logger)fConfig.getServletContext().getAttribute("Logger");
	}

}
