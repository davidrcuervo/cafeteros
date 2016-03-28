package ca.cafeteros.listeners;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import ca.cafeteros.utilities.*;
import ca.cafeteros.entities.*;
import java.io.IOException;


public class LoadUtilities implements ServletContextListener {
	
	final String LESS_RHINO_FILENAME = "less-rhino-1.7.5.js";
	final String LESSC_RHINO_FILENAME = "lessc-rhino-1.7.5.js";
	
	private Simplifier simplifier;
	private LessCompiler lessCompiler;
	private Logger log;
	private EntityManagerFactory emfactory;
	private EntityManager entityManager;
	private EntityFinder dbFinder;
	private DB db;
	private Mailer mailer;
		
    public void contextDestroyed(ServletContextEvent arg0)  {
    	log.info("Destroying servlet context....");
    	simplifier.deleteSimplifyFiles();
    	//lessCompiler.deleteCompiledFiles();
    	entityManager.close();
    	emfactory.close();
    }

	
    public void contextInitialized(ServletContextEvent event)  {
    	
    	ServletContext sc = event.getServletContext();
    	
    	initializeLogger(sc);
    	initializeDatabase(sc);
    	//compileLessStyles(sc);
    	simplifyAssets(sc);
    	initializeMailer(sc);
    }
    
    private void initializeLogger(ServletContext sc){
    	String logsFile = sc.getRealPath((String)sc.getInitParameter("logs-file"));
    	
    	log = new Logger();
    	log.setUserID(17);
    	//log.setFile(logsFile);
    	log.debug("Logs file is: " + log.getFilePath());
    	
    	sc.setAttribute("Logger", log);
    	
    	
    	log.debug("Logger has been initialized");
    }
    
    private void initializeDatabase(ServletContext sc){
    	log.info("Initializing database....");
    	
    	String persistenceUnitName = (String)sc.getInitParameter("persistence-unit-name");
    	
    	try{
    		emfactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    		entityManager = emfactory.createEntityManager();
    		//log.setDatabaseConnection(entityManager);
    		
    		dbFinder = new EntityFinder(entityManager, log);
    		log.debug("The application has been succesfully conected to the database");
    		
    		String assets = dbFinder.getSetting("assets_public_folder").getValue();
    		sc.setAttribute("assets", assets);
    		
    		db = new DB(log, emfactory);
    		sc.setAttribute("db", db);
    		sc.setAttribute("EntityManagerFactory", emfactory);
    		
    	}catch (IllegalStateException ex){
    		log.emergency("Can not create connection to the database because entity manager has been closed.");
    		System.exit(1);
    	}catch(PersistenceException ex){
    		log.emergency(ex.getMessage());
    		System.exit(1);
    	}catch(IOException ex){
    		log.error(ex.getMessage());
    	}
    }

    private void compileLessStyles(ServletContext sc){
    	log.info("Compiling less files....");
    	
    	String stylesSourceFolder;
    	String stylesCompiledFolder;
    	String thirdCodeFolder;
    	lessCompiler = new LessCompiler(log);
    	
    	try{	
    		stylesSourceFolder = sc.getRealPath(dbFinder.getSetting("styles_source_folder").getValue());
    		stylesCompiledFolder = sc.getRealPath(dbFinder.getSetting("styles_compiled_folder").getValue());
    		thirdCodeFolder = sc.getRealPath(dbFinder.getSetting("third_code_folder").getValue());
    	
	    	lessCompiler.setLessFolder(stylesSourceFolder);
			lessCompiler.setCssFolder(stylesCompiledFolder);
			lessCompiler.setJsLessRhino(thirdCodeFolder + "/" + LESS_RHINO_FILENAME);
			lessCompiler.setJsLesscRhino(thirdCodeFolder + "/" + LESSC_RHINO_FILENAME);
			lessCompiler.compile();
	    	
	    	log.info("....Less files has been compiles succesfully. Compiled path folder is: " + stylesCompiledFolder);
    	}catch (IOException ex){
    		log.emergency("It was not possible to compile styles");
    		log.emergency(ex.getMessage());
    	}
    }
    
    private void simplifyAssets(ServletContext sc){
    	log.info("Simplifying assets....");
    	
    	String assetsPublicFolder;
    	String stylesFolder;
    	String scriptsFolder;
    	String jsSimplifier;
    	
    	simplifier = new Simplifier(log);
    	
    	try{
    		assetsPublicFolder = sc.getRealPath(dbFinder.getSetting("assets_public_folder").getValue());
    		stylesFolder = sc.getRealPath(dbFinder.getSetting("styles_compiled_folder").getValue());
    		scriptsFolder = sc.getRealPath(dbFinder.getSetting("scripts_folder").getValue());
    		jsSimplifier = sc.getRealPath(dbFinder.getSetting("js_simplifier").getValue());
    		
    		simplifier.setJsSimplifier(jsSimplifier);
    		simplifier.setAssetsFolder(assetsPublicFolder);
    		simplifier.setStylesFolder(stylesFolder).setScriptsFolder(scriptsFolder);
        	simplifier.simplifyFiles();
        	
        	log.info(".... assets has been simplified succesfully");
    		
    	}catch (IOException ex){
    		log.emergency("....It was not possible to simplify assets");
    		log.emergency(ex.getMessage());
    	}
    }

    private void initializeMailer(ServletContext sc){
    	log.info("Initializing mailer");
    	
    	try{
    		String server = dbFinder.getSetting("email_smtp_address").getValue();
    		String port = dbFinder.getSetting("email_smtp_port").getValue();
    		String username = dbFinder.getSetting("email_username").getValue();
    		String address = dbFinder.getSetting("email_address").getValue();
    		String password = dbFinder.getSetting("email_password").getValue();
    		
    		mailer = new Mailer(server, Integer.parseInt(port), log);
    		mailer.setUsername(username);
    		mailer.setFrom(address);
    		mailer.setPassword(password);
    		
    		sc.setAttribute("mailer", mailer);
    		
    	}catch(IOException ex){
    		log.error("Mailer failed to initalize. Error message: " + ex.getMessage());
    	}
    }
}
