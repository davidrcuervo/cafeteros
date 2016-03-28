package ca.cafeteros.entities;

import ca.cafeteros.utilities.Logger;
import javax.persistence.*;
import java.io.IOException;

public class EntityFinder {
	
	private EntityManager em;
	private Logger log;
	
	public EntityFinder(EntityManager em){
		this.em = em;
		this.log = new Logger();
	}
	
	public EntityFinder(EntityManager em, Logger log){
		 this.em = em;
		 this.setLog(log);
	}
	
	public void setLog(Logger log){
		this.log = log;
	}
	
	//IMPORTANT: This method can not use logger because it is being call
	//as it is being called by Logger class it can generates a infinitive loop
	public Setting getSetting(String parameter) throws IOException{
		
		Setting setting = null;
		String queryName = "Setting.findByParameter";
		
		try{
			TypedQuery<Setting> query = em.createNamedQuery(queryName, Setting.class);
			query.setParameter("parameter", parameter);
			setting = query.getSingleResult();
			
		}catch (IllegalArgumentException ex){
			throw new IOException("It was not possible to find the parameter " + parameter.toUpperCase() + " in settings table. -> ERROR: " + ex.getMessage());
		}catch(NoResultException ex){
			throw new IOException("The setting: " + parameter.toUpperCase() + " does not exist in the settings table");
		}catch(NonUniqueResultException ex){
			throw new IOException("The setting: " + parameter.toUpperCase() + " has more than one result");
		}
			
		return setting;
	}
}
