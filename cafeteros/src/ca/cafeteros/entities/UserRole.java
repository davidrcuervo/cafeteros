package ca.cafeteros.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the user_roles database table.
 * 
 */
@Entity
@Table(name="user_roles")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole extends DbTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "users_roles_id_seq", sequenceName = "users_roles_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_roles_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;
	
	@Column(name = "\"username\"", nullable = false)
	private String username;
	
	@Column(name = "\"role\"", nullable = false)
	private String role;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable=false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"userID\"")
	private User user;
	
	public UserRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Date getModifiedDate(){
		return this.modified;
	}
	
	public Date getCreatedDate(){
		return this.created;
	}
	
	public void setUser(User user){
		this.user = user;
		
		if(!user.getUserRoles().contains(this)){
			user.getUserRoles().add(this);
		}
	}
	
	public User getUser(){
		return this.user;
	}
	

}