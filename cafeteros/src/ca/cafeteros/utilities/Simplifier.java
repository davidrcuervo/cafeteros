package ca.cafeteros.utilities;

import java.io.*;

public class Simplifier {
	
	private File jsSimplifier;
	private File assetsFolder;
	private File stylesFolder;
	private File scriptsFolder;
	private Logger log;

	public static void main(String[] args) {
		
		Simplifier test = new Simplifier();
		try{
			test.setJsSimplifier("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/lib/compiler.jar");
			test.setAssetsFolder("/home/myself/eclipseProjects/cafeteros/WebContent/assets");
			test.setStylesFolder("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/view/styles/bin").setScriptsFolder("/home/myself/eclipseProjects/cafeteros/WebContent/WEB-INF/view/scripts");
			test.deleteSimplifyFiles();
			test.simplifyFiles();
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public Simplifier(){
		log = new Logger();
	}
	
	public Simplifier(Logger log){
		this.log = log;
	}
	
	public void setJsSimplifier(String jsSimplifierPath) throws IOException{
		
		jsSimplifier = new File(jsSimplifierPath);
		
		if(jsSimplifier.getName().endsWith("compiler.jar") && jsSimplifier.exists() && jsSimplifier.canRead() && jsSimplifier.canExecute()){
			log.info("JS for simplify scripts has been set succesfully");
		}else{
			log.error("JS script required to simplify javascripts is not valid. FILE PATH: " + jsSimplifierPath);
			throw new IOException("JS script required to simplify javascripts is not valid. FILE PATH: " + jsSimplifierPath);
		}
	}
	
	public void setAssetsFolder(String assetsFolderPath) throws IOException{
		log.info("Setting assets folder. FOLDER PATH: " + assetsFolderPath);
		
		assetsFolder = new File(assetsFolderPath);
		
		if(!assetsFolder.exists()){
			assetsFolder.mkdir();
		}
		
		if(assetsFolder.exists() && this.assetsFolder.isDirectory() && this.assetsFolder.canWrite() && assetsFolder.canRead()){
			log.info("Public assets folder has succesfully set. FOLDER PATH: " + assetsFolder.getAbsolutePath());
		}else{
			assetsFolder = null;
			log.error("It was not possible to set the public asset folder" + assetsFolderPath);
			throw new IOException("It was not possible to set the public asset folder" + assetsFolderPath);
		}
	}
	
	public Simplifier setStylesFolder(String stylesFolderPath) throws IOException{
		log.info("Setting styles folder for simplifying. FOLDER PATH: " + stylesFolderPath);
		
		stylesFolder = new File(stylesFolderPath);
		
		if(stylesFolder.exists() && stylesFolder.canRead()){
			log.info("Styles folder for simplify has been set succesfully. FOLDER PATH: " + stylesFolder.getAbsolutePath());
		}else{
			stylesFolder = null;
			log.error("The styles folder to simplify is invalid. FOLDER PATH: " + stylesFolderPath);
			throw new IOException("The styles folder to simplify is invalid. FOLDER PATH: " + stylesFolderPath);
		}
		
		return this;
	}
	
	public Simplifier setScriptsFolder(String scriptsFolderPath) throws IOException{
		log.info("Setting scripts folder for simplify. FOLDER PATH: " + scriptsFolderPath);
		
		scriptsFolder = new File(scriptsFolderPath);
		
		if(scriptsFolder.exists() && scriptsFolder.canRead()){
			log.info("Scripts folder for simplify has been set succesfully. FOLDER PATH: " + scriptsFolder.getAbsolutePath());
		}else{
			scriptsFolder = null;
			log.error("The scripts folder to simplify is invalid. FOLDER PATH: " + scriptsFolderPath);
			throw new IOException("The scripts folder to simplify is invalid. FOLDER PATH: " + scriptsFolderPath);
		}
		
		return this;
	}
	
	public void simplifyFiles() throws IOException{
		log.info("simplifing files...");
		simplifyFilesInFolder(this.stylesFolder);
		simplifyFilesInFolder(this.scriptsFolder);
	}
	
	private void simplifyFilesInFolder(File folder) throws IOException{
		log.info("Simplifying files in folder. FOLDER PATH: " + folder.getAbsolutePath());
		
		ProcessBuilder pb;
		Process p;
		//int result;
		
		for(File file : folder.listFiles()){
			if(file.isDirectory()){
				simplifyFilesInFolder(file);
			}else{
				log.info("Simplifying file. FILE NAME: " + file.getName());
				
				String simplifyFile = assetsFolder.getAbsolutePath() + "/" + file.getName();
							
				try{
										
					if(file.getAbsolutePath().endsWith(".css")){
						String[] arguments = {"-v", file.getAbsolutePath(), "-o", simplifyFile, "--charset", "utf-8"};
						com.yahoo.platform.yui.compressor.YUICompressor.main(arguments);
						
					}else if(file.getAbsolutePath().endsWith(".js")){
						
						pb = new ProcessBuilder("java", "-jar", jsSimplifier.getAbsolutePath(), "--js", file.getAbsolutePath(), "--js_output_file", simplifyFile);
						p = pb.start();
						p.waitFor();
					}
					
				}catch(Exception ex){
					log.error("ERROR. Simplifying:" + file.getName());
					throw new IOException(ex.getMessage());
				}
			}
		}
	}
	
	public void deleteSimplifyFiles(){
		deleteFiles(this.assetsFolder);
	}
	
	private void deleteFiles(File folder){
		log.info("Deleting files in folder. FOLDER PATH: " + folder.getAbsolutePath());
		
		for(File file : folder.listFiles()){
			if(file.isDirectory()){
				deleteFiles(file);
			} else {
				file.delete();
			}
		}
	}
}
