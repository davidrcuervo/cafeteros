package ca.cafeteros.web;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.laetienda.db.DbManager;
import com.laetienda.db.SqlException;
import com.laetienda.db.DbException;
import com.laetienda.lang.LangException;
import com.laetienda.lang.LangManager;
import com.laetienda.logger.LoggerManager;
import com.laetienda.logger.JavaLogger;
import com.laetienda.logger.LoggerException;
import com.laetienda.multimedia.MediaManager;
import com.laetienda.multimedia.MultimediaException;
import com.laetienda.notes.NotesException;
import com.laetienda.notes.NotesManager;

public class Load implements ServletContextListener{
	
	private File directory;
	private LoggerManager logManager;
	private JavaLogger log;
	private LangManager langManager;
	private DbManager dbManager;
	private MediaManager mediaManager;
	private NotesManager notesManager;
	
	public void contextInitialized(ServletContextEvent arg0) {
		
		ServletContext sc = arg0.getServletContext();
		directory = new File(sc.getInitParameter("directory"));
		
		//Start logger
		try{
			logManager = new LoggerManager(directory);
			sc.setAttribute("logManager", logManager);
			log = logManager.createJavaLogger();
			log.info("Logger library has started successfully");
		}catch(LoggerException ex){
			ex.printStackTrace();
			exit();
		}
		
		//start database
		try{
			dbManager = new DbManager(directory);
			sc.setAttribute("dbManager", dbManager);
			log.info("Database library has started successfully");
		}catch(DbException ex){
			log.exception(ex);
			exit();
		}
		
		//start multimedia library
		try{
			mediaManager = new MediaManager(directory);
			sc.setAttribute("mediaManager", mediaManager);
			log.info("Multimedia library has started successfully");
		}catch(MultimediaException ex){
			log.exception(ex);
			exit();
		}
		
		//start notes library
		try{
			notesManager = new NotesManager(directory);
			sc.setAttribute("notesManager", notesManager);
			log.info("Notes library has started successfully");
		}catch(NotesException ex){
			log.exception(ex);
			exit();
		}
		
		//start language
		try{
			langManager = new LangManager(directory, logManager.createJavaLogger());
			sc.setAttribute("langManager", langManager);
			
			try{
				langManager.importLang();
				
			}catch(SqlException ex){
				log.exception(ex);
			}
			
			log.info("Notes library has started successfully");
		}catch(LangException ex){
			log.exception(ex);
			exit();
		}
		
		log.info("CAFETEROS WEB APPLICATION HAS LOADED SUCCESFULLY");
	}
	
	public void contextDestroyed(ServletContextEvent arg0){
		close();
	}
	
	private void close(){
		
		if(notesManager != null){
			
		}
		
		if(langManager != null){
			
			try{
				langManager.exportLang();
			}catch(SqlException ex){
				log.critical("Failed to export language to CSV file. Information might be lost");
				log.exception(ex);
			}
		}
		
		if(dbManager != null){
			dbManager.close();
		}
		
		log.info("cafeteros web application has closed successfully");
		if(logManager != null){
			logManager.close();
		}
	}
	
	private void exit(){
		
		if(log == null){
			System.out.println("Failed to start cafeteros web application");
		}else{
			log.critical("Failed to start cafeteros web application");
		}
		
		close();
		System.exit(-1);
	}
}
