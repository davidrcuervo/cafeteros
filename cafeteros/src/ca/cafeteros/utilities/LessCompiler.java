package ca.cafeteros.utilities;

import java.io.*;

//import com.google.common.io.Files;
import ca.cafeteros.utilities.Logger;

public class LessCompiler {
	
	private File lessFolder;
	private File cssFolder;
	private File jsLessRhino;
	private File jsLesscRhino;
	private Logger log;

	public static void main(String[] args) {
		
		LessCompiler compiler = new LessCompiler();
		
		try{
			compiler.setLessFolder("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/view/styles/src");
			compiler.setCssFolder("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/view/styles/bin");
			compiler.setJsLessRhino("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/third/less-rhino-1.7.5.js");
			compiler.setJsLesscRhino("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/third/lessc-rhino-1.7.5.js");
			compiler.deleteCompiledFiles();
			compiler.compile();
		}catch (IOException ex){
			compiler.log().error(ex.getMessage());
		}
	}
	
	public LessCompiler(){
		log = new Logger();
	}
	
	public LessCompiler(Logger log){
		this.log = log;
	}
	
	public Logger log(){
		return this.log;
	}
	
	public LessCompiler setLessFolder(String lessFolder) throws IOException{
		log.info("Setting less source folder. FOLDER PATH: " + lessFolder);
		
		this.lessFolder = new File(lessFolder);
		
		if(this.lessFolder.exists() && this.lessFolder.isDirectory()){
			log.info("Folder with style sources has been succesfully set. FOLDER PATH: " + this.lessFolder.getPath() );
		}else{
			log.error("Folder with syle sources is invalid. FOLDER PATH: " + lessFolder);
			this.lessFolder = null;
			throw new IOException("Folder with syle sources is invalid. FOLDER PATH: " + lessFolder);
		}
		
		return this;
	}
	
	public LessCompiler setCssFolder(String cssFolder) throws IOException{
		log.info("Setting css compiled folder. FOLDER PATH: " + cssFolder);
		this.cssFolder = new File(cssFolder);
		
		if(!this.cssFolder.exists()){
			this.cssFolder.mkdir();
		}
		
		if(this.cssFolder.exists() && this.cssFolder.isDirectory()){
			log.info("Folder for compiled styles has been succesfully set. FOLDER PATH: " + this.cssFolder.getPath());
		} else{
			log.error("CSS folder for compiled styles does not exist. FOLDER PATH: " + this.cssFolder.getPath());
			throw new IOException("CSS folder for compiled styles does not exist. FOLDER PATH: " + this.cssFolder.getPath());
		}
		
		return this;
	}
	
	public LessCompiler setJsLessRhino(String jsLessRhino){
		log.info("Setting jsLessRhino file. FILE PATH: " + jsLessRhino);
		this.jsLessRhino = new File(jsLessRhino);		
		return this;
	}
	
	public LessCompiler setJsLesscRhino(String jsLesscRhino){
		log.info("Setting jsLesscRhino file. FILE PATH: " + jsLesscRhino);
		this.jsLesscRhino = new File(jsLesscRhino);
		return this;
	}
	
	public void compile() throws IOException{
		log.info("Compiling less files");
		compileFolder(this.lessFolder);
	}
	
	private void compileFolder(File folder) throws IOException{
		log.info("Compiling files in folder. FOLDER PATH: " + folder.getAbsolutePath());
		
		SystemExitPrevention exitPrevention = new SystemExitPrevention();
		System.setSecurityManager(exitPrevention);
		
		for(File file : folder.listFiles()){
			
			if(file.isDirectory()){
				if(file.getName().endsWith("_NoCompile")){
					log.info("The folder will be skiped for compiling. FOLDER NAME: " + file.getName());
				}else{
					compileFolder(file);
				}
				
			}else{
				String cssFile = cssFolder.getAbsolutePath() + "/" + file.getName().replace(".less", ".css");
				
				if(file.getAbsolutePath().endsWith(".less")){
					log.info("Compiling less file. FILE NAME: " + file.getName());
					
					String[] arguments = {"-f", jsLessRhino.getAbsolutePath(), jsLesscRhino.getAbsolutePath(), file.getAbsolutePath(), cssFile};
					
					try{
						org.mozilla.javascript.tools.shell.Main.main(arguments);
					} catch (Throwable x){
						if(x instanceof SecurityException){
							log.info("Intercepted System.exit()");
						}else{
							log.error(x.getMessage());
							throw new IOException("It was not possible to compile less file. FILE PATH: " + file.getAbsolutePath());
						}
					}finally{
						
					}
				} else if (file.getAbsolutePath().endsWith(".css")){
					log.info("Compying css file. FILE NAME: " + file.getName());
					
					try{
						ProcessBuilder pb = new ProcessBuilder("cp", file.getAbsolutePath(), cssFile);
						Process p = pb.start();
						p.waitFor();
						
					}catch(Exception ex){
						log.error(ex.getMessage());
						throw new IOException("CSS file has not been copied to styles compiled folder. FILE PATH: " + file.getAbsolutePath());
					}
				}
			}
		}
		log.info("Less files in folder has been compiled succesfully. FOLDER PATH: " + folder.getAbsolutePath());
	}
	
	public void deleteCompiledFiles(){
		deleteStyles(this.cssFolder);
	}
	
	private void deleteStyles(File folder){
		log.info("Deleting compiled styles in folder. FOLDER PATH: " + folder.getAbsolutePath());
		
		for(File file: folder.listFiles()){
			if(file.isDirectory()){
				deleteStyles(file);
			} else {
				file.delete();
			}
		}
	}

}
