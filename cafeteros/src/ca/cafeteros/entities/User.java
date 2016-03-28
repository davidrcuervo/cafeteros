package ca.cafeteros.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;
import ca.cafeteros.utilities.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;



/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQueries({
	@NamedQuery(name="User.findAll", 
			query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByEmail",
			query="SELECT u FROM User u WHERE u.email = :email")
})
public class User extends DbTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;

	private String email;

	@Column(name="\"fName\"")
	private String fName;

	@Column(name="\"lName\"")
	private String lName;

	private String username;
	
	@Column(name="\"password\"")
	private String password;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@OneToMany( mappedBy = "user")
	private List<UserRole> userRoles;
	
	public User() {
		super();
		this.userRoles = new ArrayList<UserRole>();
	}
	
	public User(Logger log){
		super(log);
		this.userRoles = new ArrayList<UserRole>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		log.info("Setting email for user: " + email);
		
		if(email == null || email.isEmpty()){
			addError("email", "Your email is empty.");
		}else{
			if(email.length() > 254){
				addError("email", "Your email can not contain more than 254 characters");
			}else{
				try{
					InternetAddress internetAddress = new InternetAddress(email);
					internetAddress.validate();
				}catch(AddressException ex){
					addError("email", "Your email is not a valid address");
				}
			}
		}
		
		this.email = email;
	}

	public String getFName() {
		return this.fName;
	}
	
	public String getFname(){
		return this.fName;
	}

	public void setFName(String firstName) {
		log.info("Setting first name for user object. fName: " + firstName);
		
		if(firstName == null || firstName.isEmpty()){
			addError("fname", "Your first name is empty");
		} else {
		
			if(firstName.length() > 254){
				addError("fname", "Your first name can not contain more than 254 characters");
			}
			
			if(!firstName.matches("[a-zA-Z\\s]+")){
				addError("fname", "Your first name con not contain numbers or special charecters");
			}
			
			if(Character.isWhitespace(firstName.charAt(0))){
				addError("fname", "Your first name can not start with a white espace");
			}
		}
		
		this.fName = firstName.toLowerCase();
	}

	public String getLName() {
		return this.lName;
	}
	
	public String getLname(){
		return this.lName;
	}

	public void setLName(String lastName) {
		log.info("Setting last name of user. lname: " + lastName);
		
		
		if(lastName == null || lastName.isEmpty()){
			addError("lname", "Your last name is empty.");
		} else{
			if(lastName.length() > 254){
				addError("lname" , "Your last name can not contain more than 254 characters");
			}
			
			if(Character.isWhitespace(lastName.charAt(0))){
				addError("lname", "Your last name can not start with a white space");
			}
			
			if(!lastName.matches("[a-zA-Z\\s]+")){
				addError("lname", "Your last name can not contain number or special characters");
			}
		}
		
		this.lName = lastName.toLowerCase();
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		log.info("Setting password for user. password: " + password);
		
		if(password.length() > 64){
			addError("password", "An internal error has ocurred while saving your password");
			log.error("The encrypted password contains more 64 characters");
			this.password = null;
		} else {
			this.password = password;
		}
	}
	
	public boolean isPasswordMatch(String pass){
		log.info("Verifying if password match");
		
		if(this.password.equals(pass)){
			return true;
		}
		
		return false;
	}
	
	public boolean validatePassword(String pass1, String pass2){
		log.info("Validationg password for user.");
		
		Boolean result = true;
		
		if(pass1 == null || pass2 == null || pass1.isEmpty() || pass2.isEmpty()){
			addError("password", "Password and/or confirmation password is empty.");
			result = false;
		}else{
			if(pass1.matches("[\\s]")){
				addError("password", "Your password can not contain white spaces");
			}
			
			if(!pass1.equals(pass2)){
				addError("password", "Password and confirmation password don't match. Please verify that they are exactly the same");
				result=false;
			}
		}
		
		return result;
	}
	
	public Date getModifiedDate(){
		return this.modified;
	}
	
	public Date getCreatedDate(){
		return this.created;
	}
	
	public void addRole(UserRole role){
		this.userRoles.add(role);
		if(role.getUser() != this){
			role.setUser(this);
		}
	}
	
	public List<UserRole> getUserRoles(){
		return this.userRoles;
	}
}