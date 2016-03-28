package ca.cafeteros.beans;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.utilities.Logger;
import ca.cafeteros.entities.User;
import ca.cafeteros.entities.Detail;
import ca.cafeteros.entities.ParameterValue;

public class Team implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private ca.cafeteros.entities.Team team;
	private DB db;
	private Logger log;
	
	public Team(){
		
	}
	
	public Team(Logger log, DB db, ca.cafeteros.entities.Team team){
		this.log = log;
		this.db = db;
		this.team = team;
	}
	
	public ca.cafeteros.entities.Team getTeam(){
		return this.team;
	}
	
	public Map<String, User> getPlayers(){
		log.info("finding player for team");
		
		Map<String, User> players = new HashMap<String, User>();
		
		try{
			List<Detail> details = db.getDetailsByParameter("player status in team", team.getId());
			
			for(Detail detail: details){
				log.debug("parameter value: " + detail.getParameterValue().getValue() + " user ID: " + Integer.toString(db.getUserById(detail.getTablebId()).getId()));
				players.put(detail.getParameterValue().getValue(), db.getUserById(detail.getTablebId()));
			}
			
		}catch(Exception ex){
			log.error("No player have been found");
			log.error(ex.getMessage());
		}
		return players;
	}
	
	public List<ParameterValue> getAllPlayerStatus(){
		log.info("Getting all available status for players");
		
		return db.getParameterByName("player status in team").getValues();
	}
}
