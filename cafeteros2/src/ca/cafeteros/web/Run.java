package ca.cafeteros.web;

import java.io.File;
import com.laetienda.tomcat.Controller;

public class Run {
	
	private File directory;
	
	private Run(){
		directory = new File("");
	}

	public static void main(String[] args) {
		
		System.out.println("Starting cafeteros website");
		
		Run run = new Run();
		
		try{
			Controller tomcat  = new Controller(run.directory);
			tomcat.parseArguments(args);
			
			//System.out.println(run.directory.getAbsolutePath() + File.separator + "WebContent");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
