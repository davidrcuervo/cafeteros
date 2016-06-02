package ca.cafeteros.entities;

import java.io.Serializable;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
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
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="\"introduction\"", nullable=false, unique=true)
	private TextReference introduction;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"name\"", nullable=false, unique=true)
	private String name;
	
	@Column(name="\"urlencodedname\"", nullable=false, unique=true)
	private String urlEncodedName;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="\"description\"", nullable=true, unique=true)
	private TextReference description;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="\"agreement\"", nullable=false, unique=true)
	private TextReference agreement;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="\"members\"", nullable=true, unique=true)
	private DetailIndex members;
	
	public Team() {
		super();
		initObjects();
	}
	
	public Team(Logger log){
		super(log);
		initObjects();
		
	}
	
	private void initObjects(){
		this.agreement = new TextReference();
		this.introduction = new TextReference();
		this.description = new TextReference();
		this.members = new DetailIndex();
	}

	public Integer getId() {
		return this.id;
	}

	public void setAgreement(String agreement){
		log.info("Setting agreement. agreement: \"" + agreement + "\"");
		
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

	public void setDescription(String description){
		log.info("Setting description. description: \"" + description + "\"");
		
		this.description.setField("description");
		this.description.setTable("Teams");
		this.description.setText(description);
	}
	
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
		log.info("Setting name for new team. name: " + name);
		
		if(name == null || name.isEmpty()){
			addError("name", "The team name is empty, make sure that you put a unique name to the team");
		}else{
			if(name.length() > 64){
				addError("name", "Make sure that the name of the team has 64 characters maximun");
			}
		}
		
		this.name = name;
		this.members.setTable("Teams");
		setUrlEncodedName();
	}
	
	public List<Detail> getMembers(){
		log.info("Getting list of IDs of members of team. $teamURL: " + this.getUrlEncodedName());
		
		return members.getDetails();
	}
	
	public void changeMemberStatus(User user, ParameterValue status){
		log.info("Adding request to team. $teamName: " + this.name);
		
		Boolean flag = true;
		
		if(user == null){
			
		}else{
			log.info("Changing status of player. $userName: " + user.getFname() + user.getLname() + " $newStatus: " + status.getValue());
			
			for(Detail member : members.getDetails()){
				
				if(member.getTablebId() == user.getId()){
					log.debug("This user has already request to be player of the team.");
					member.setParameterValue(status);
					
					flag = false;
					break;
				}
			}
			
			if(flag){
				Detail member = new Detail();
				member.setParameterValue(status);
				member.setTableaId(this.getId());
				member.setTablebId(user.getId());
				member.setDetailIndex(this.members);
				members.getDetails().add(member);
			}
		}
	}
}