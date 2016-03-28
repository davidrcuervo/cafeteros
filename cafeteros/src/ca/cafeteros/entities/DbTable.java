package ca.cafeteros.entities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import ca.cafeteros.utilities.Logger;

public abstract class DbTable {
	
	protected Logger log;
	private HashMap<String, List<String>> errors;
	
	protected DbTable(){
		log = new Logger();
		errors = new HashMap<String, List<String>>();
	}
	
	protected DbTable(Logger log){
		this.log = log;
		errors = new HashMap<String, List<String>>();
	}
	
	public void addError(String list, String error){
		log.info("Adding error: \"" + error + "\" to the list: \"" + list + "\"...");
		
		List<String> errorList;
		
		if(errors.get(list) == null){
			errorList = new ArrayList<String>();
		} else{
			errorList = errors.get(list);			
		}
		
		errorList.add(error);
		errors.put(list, errorList);
		
		log.info("... error added succesfully.");
	}
	
	public HashMap<String, List<String>> getErrors(){
		return this.errors;
	}
	
	public abstract Integer getId();

}
