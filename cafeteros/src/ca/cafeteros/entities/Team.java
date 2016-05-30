package ca.cafeteros.entities;

import java.io.Serializable;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.persistence.*;

//import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;

import ca.cafeteros.utilities.Logger;

@Entity
@Table(name="teams")
@NamedQueries({
	@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t"),
	@NamedQuery(name="Team.findByName", query="SELECT t FROM Team t WHERE t.name = :teamName"),
	@NamedQuery(name="Team.findByUrlEncodedName", query="SELECT t FROM Team t WHERE t.urlEncodedName = :urlEncodedName"),
})

public class Team extends DbTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "teams_id_seq", sequenceName = "teams_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_id_seq")
	@Column(name="id", updatable=false, unique=true)
	private Integer id;
	/*
	@Column(name="\"agreementId\"", nullable=false, unique=true)
	private Integer agreementId;
	*/
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	/*
	@Column(name="\"descriptionId\"", nullable=true, unique=false)
	private Integer descriptionId;
	*/
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="\"introduction\"", nullable=false, unique=true)
	private TextReference introduction;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"name\"", nullable=false, unique=true)
	private String name;
	
	@Column(name="\"urlencodedname\"", nullable=false, unique=true)
	private String urlEncodedName;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="\"description\"", nullable=true, unique=true)
	private TextReference description;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="\"agreement\"", nullable=false, unique=true)
	private TextReference agreement;
	
	public Team() {
		super();
	}
	
	public Team(Logger log){
		super(log);
	}

	public Integer getId() {
		return this.id;
	}
/*
	public void setAgreementId(Integer agreementId) {
		log.info("Setting agreement id. aggrementId: " + Integer.toString(agreementId));
		
		if(agreementId == 0){
			addError("enrollmentAgreement", "There was and error while registering the enrrollment agreement for this team");
		}
		this.agreementId = agreementId;
	}
*/	
	public void setAgreement(String agreement){
		log.info("Setting agreement. agreement: \"" + agreement + "\"");
		
		this.agreement = new TextReference();
		this.agreement.setField("agreement");
		this.agreement.setTable("Team");
		this.agreement.setText(agreement);
	}
	
	public TextReference getAgreement(){
		log.info("getting agreement");
		
		return agreement;
	}

	public Date getCreated() {
		return this.created;
	}
/*
	public void setDescriptionId(Integer descriptionId) {
		log.info("Setting description identifier. descriptionId: " + Integer.toString(descriptionId));
		
		if(descriptionId == 0){
			addError("enrollmentAgreement", "There was and error while registering the team description");
		}
		
		this.descriptionId = descriptionId;
	}
	*/
	public void setDescription(String description){
		log.info("Setting description. description: \"" + description + "\"");
		
		this.description = new TextReference();
		this.description.setField("description");
		this.description.setTable("Teams");
		this.description.setText(description);
	}
/*	
	public Integer getDescriptionId() {
		return this.descriptionId;
	}
*/	
	public TextReference getDescription(){
		log.info("getting description");
		
		return description;
	}

	public TextReference getIntroduction() {
		log.info("getting introduction to team");
		
		return introduction;
	}

	public void setIntroduction(String introduction) {
		log.info("Setting introduction for new team. introduction: \"" + introduction + "\"");
		
		if(introduction == null || introduction.isEmpty()){
			addError("introduction", "The introduction for the team must not be empty");
		}else{
			if(introduction.length() > 254){
				addError("introduction", "The introduction of the team must have less than 254 characters");
			}
		}
		
		this.introduction = new TextReference();
		this.introduction.setField("introduction");
		this.introduction.setTable("Teams");
		this.introduction.setText(introduction);
	}

	public Date getModified() {
		return this.modified;
	}

	public String getName() {
		return this.name;
	}
	
	public String getUrlEncodedName(){
		log.info("getting named Url Encoded. $nameUrlEncoded = " + this.urlEncodedName);
		/* 
		String result;
		
		try{
			result = URLEncoder.encode(name, "UTF-8");
		}catch(UnsupportedEncodingException ex){
			result = "none";
		}
		*/
		return this.urlEncodedName;
	}
	
	private void setUrlEncodedName(){
		log.info("encoding name of the team to URL standar");
		
		try{
			this.urlEncodedName = URLEncoder.encode(this.name, "UTF-8");
		}catch(UnsupportedEncodingException ex){
			log.critical("Wrong collation encoder");
			addError("name", "Make sure that the name of the team doesn't have anormal characters");
		}
	}

	public void setName(String name) {
		log.info("Settin name for new team. name: " + name);
		
		if(name == null || name.isEmpty()){
			addError("name", "The team name is empty, make sure that you put a unique name to the team");
		}else{
			if(name.length() > 64){
				addError("name", "Make sure that the name of the team has 64 characters maximun");
			}
		}
		
		this.name = name;
		setUrlEncodedName();
	}
}