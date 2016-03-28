package ca.cafeteros.beans;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;

public class PageResources implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<String> styles = new ArrayList<String>();
	private List<String> scripts = new ArrayList<String>();
	private String baseUrl = "http://localhost:8080";
	private String scheme = "http";
	private String contextPath = "cafeteros";
	private String serverName = "localhost";
	private int serverPort = 80;
	private String word = "";
	
	public PageResources(){
		
	}
	
	public void setAddStyle(String style){
		styles.add(style);
	}
	
	public List<String> getStyles(){
		return this.styles;
	}
	
	public void setAddScript(String script){
		scripts.add(script);
	}
	
	public List<String> getScripts(){
		return this.scripts;
	}
	
	public void setScheme(String scheme){
		this.scheme = scheme;
	}
	
	public String getScheme(){
		return scheme;
	}
	
	public void setServerName(String serverName){
		this.serverName = serverName;
	}
	
	public String getServerName(){
		return this.serverName;
	}
	
	public void setServerPort(int serverPort){
		//this.serverPort = Integer.parseInt(serverPort);
		this.serverPort = serverPort;
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public void setContextPath(String contextPath){
		this.contextPath = contextPath;
	}
	
	public String getContextPath(){
		return contextPath;
	}
	
	public void setBaseUrl(String baseUrl){
		this.baseUrl = baseUrl;
	}
	
	public String getBaseUrl(){
		
		baseUrl = getScheme() + "://" + getServerName();
	
		if((getScheme().equals("http") && getServerPort() == 80) || (getScheme().equals("https") && getServerPort() == 443 )){
			
		}else{
			baseUrl += ":" + getServerPort();
		}
		
		baseUrl += getContextPath();
		
		return baseUrl;
	}
	
	public void setWord(String word){
		this.word = word;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getEncodedWord(){
		String encodedWord;
		
		try{
			encodedWord = Base64.getEncoder().encodeToString(word.getBytes("utf-8"));
		}catch(UnsupportedEncodingException ex){
			encodedWord = word;
		}
		
		return encodedWord;
	}
	
	

}
