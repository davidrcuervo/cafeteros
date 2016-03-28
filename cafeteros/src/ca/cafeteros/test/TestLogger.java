package ca.cafeteros.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;

import ca.cafeteros.utilities.Logger;


public class TestLogger {

	
	public static void main(String[] args) {
		
		TestLogger testLogger = new TestLogger();
		//testLogger.testLogger();
		testLogger.simulateListener();
		
	} 
	
	public void testLogger(){
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("cafeteros");
		EntityManager entitymanager = emfactory.createEntityManager();
		
		String fileName = "cafeteros.log";
		
		Logger logger = new Logger();
		logger.setFile(fileName);
		logger.setDatabaseConnection(entitymanager);
		logger.setUserID(17);
		
		//{"DEBUG", "INFO", "NOTICE", "WARNING", "ERROR", "CRITICAL", "ALERT", "EMERGENCY"};
		
		logger.setLevel("DEBUG");
		logger.debug("value of a variable");
		logger.info("indication that a method is running");
		logger.notice("notice message");
		logger.warning("Warning message");
		logger.error("error message");
		logger.critical("critical message");
		logger.alert("alert message");
		logger.emergency("emergency message");
		
		entitymanager.close();
		emfactory.close();
	}

	public void simulateListener(){
		
		EntityManager entityManager;
		
		//Initialize logger
    	Logger log = new Logger();
    	log.debug("Logger has been initialized");
    	         
    	//Set logs file.
    	log.setFile("/WEB-INF/logs/cafeteros.log");
    	log.setUserID(17);
    	log.debug("file has been initialized");
    	
    	
    	//Connect to the database
    	EntityManagerFactory 	emfactory = Persistence.createEntityManagerFactory("cafeteros");
    	
    	try{
    		entityManager = emfactory.createEntityManager();
    		log.setDatabaseConnection(entityManager);
    		log.debug("The application has been succesfully conected to the database");
    		entityManager.close();
    	}catch (IllegalStateException ex){
    		log.emergency("Can not create connection to the database because entity manager has been closed.");
    		System.exit(1);
    	}
    	
    	emfactory.close();
    	
	}
}
