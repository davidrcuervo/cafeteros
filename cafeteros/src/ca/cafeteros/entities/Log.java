package ca.cafeteros.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the logs database table.
 * 
 */
@Entity
@Table(name="logs")
@NamedQuery(name="Log.findAll", query="SELECT l FROM Log l")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "logs_id_seq", sequenceName = "logs_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logs_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String level;
	
	@Column(name="\"userId\"")
	private Integer userId;
	
	@Column(name="class")
	private String class_;
	
	private String log;
	
	@Column(name = "\"method\"")
	private String method;
	
	@Column(name = "\"line\"")
	private Integer line;

	public Log() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public Date getCreatedDate() {
		return this.created;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getMethod(){
		return this.method;
	}
	
	public void setMethod(String method){
		this.method = method;
	}
	
	public Integer getLine(){
		return this.line;
	}
	
	public void setLine(Integer line){
		this.line = line;
	}
}