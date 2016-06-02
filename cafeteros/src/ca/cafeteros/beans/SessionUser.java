package ca.cafeteros.beans;

import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import ca.cafeteros.utilities.DB;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.entities.User;
import ca.cafeteros.entities.UserRole;
import ca.cafeteros.entities.Team;
import ca.cafeteros.entities.Detail;

public class SessionUser implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private User user;
	private DB db;
	private Logger log;
	//private HttpServletRequest request;
	
	public SessionUser(){
		log = new Logger();
	}
	
	public void setRequest(HttpServletRequest request){
		//this.request = request;
		log = (Logger)request.getServletContext().getAttribute("Logger");
		log.info("Getting parameters to build the user session bean");
		db = (DB)request.getServletContext().getAttribute("db");
		String email = (String)request.getRemoteUser();
		
		try{
			user = db.getUserByEmail(email);
		}catch(IOException ex){
			user = null;
			log.error("User has logged with a wrong email. But, if it is a wrong email how he logged in? email: " + email );
		}
	}
	
	public String getName(){
		try{
			return user.getFname() + " " + user.getLName();
		}catch (NullPointerException ex){
			return "No user";
		}
	}
	
	public String getEmail(){
		try{
			return user.getEmail();
		}catch (NullPointerException ex){
			return "No user";
		}
	}
	
	public boolean getIsVisitor(){
		log.info("Checking if user is visitor.");
		
		return isRole("visitor");
	}
	
	public boolean getIsManager(){
		log.info("Checking if user is manager or root.");
		
		if(isRole("manager") || isRole("root")){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean getIsExecutiveBoardMember(){
		log.info("Checking if user is executive board member");
		
		if(isRole("executive") || isRole("root")){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isRole(String role){
		log.info("Checking if user has role: " + role);
		
		try{
			Boolean result = false;
			List<UserRole> userRoles = user.getUserRoles();
			
			for(UserRole userRole : userRoles){
				log.debug("role: " + userRole.getRole());
				if(userRole.getRole().equals(role)){
					result = true;
				}
			}
			
			return result;
		}catch(NullPointerException ex){
			log.error(ex.getMessage());
			return false;
		}
	}
	
	public boolean isTeamManager(Team team){
		log.info("Checking if user is team manager");
		
		boolean result = false;
		try{
			if(isRole("root")){
				log.debug("User is root. user name: " + user.getFname() + " " + user.getLName());
				result=true;
			}else{
				List<Detail> managers = db.getDetails("Team managers", "manager", team.getId());
				
				for(Detail detail : managers){
					User manager = db.getUserById(detail.getTablebId());
					
					if(manager.getId().equals(user.getId())){
						result = true;
						break;
					}
				}
			}
		}catch(Exception ex){
			log.error(ex.getMessage());
			result = false;
		}
		return result;
	}
	
	public boolean getIsTeamManager(){
		return false;
	}
	
	public boolean isTeamMember(Team team){
		log.info("Checking if user is part of the team");
		
		boolean result = false;
		try{
			List<Detail> members = db.getDetailsByParameter("player status in team", team.getId());
			
			for(Detail detail : members){
				User member = db.getUserById(detail.getTablebId());
				
				if(member.getId().equals(user.getId())){
					result = true;
					break;
				}
			}
			/*
			if(isRole("root")){
				result=true;
			}
			*/
			
		}catch(Exception ex){
			log.error(ex.getMessage());
			result = false;
		}
		return result;
	}
	
	public boolean getIsTeamMember(){
		return false;
	}
	
}
