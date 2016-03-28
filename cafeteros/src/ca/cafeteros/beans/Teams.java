package ca.cafeteros.beans;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ca.cafeteros.entities.Team;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.DB;

public class Teams implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	//private HttpServletRequest request;
	//private List<Team> teams;
	private Logger log;
	private DB db;
	
	public Teams(){
		
	}
	
	public void setRequest(HttpServletRequest request){
		log = (Logger)request.getServletContext().getAttribute("Logger");
		log.info("Teams bean has been set");
		
		db = (DB)request.getServletContext().getAttribute("db");
	}
	
	public List<Team> getTeams(){
		
		return db.getAllTeams();
	}
}
