package ca.cafeteros.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the settings database table.
 * 
 */
@Entity
@Table(name="settings")
@NamedQueries({
	@NamedQuery(name="Setting.findAll", 
			query="SELECT s FROM Setting s"),
	@NamedQuery(name="Setting.findByParameter",
			query="SELECT s FROM Setting s WHERE s.parameter = :parameter")
})
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "settings_id_seq", sequenceName = "settings_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@Column(name="\"parameter\"", nullable = false)
	private String parameter;
	
	@Column(name="\"value\"", nullable = false)
	private String value;

	public Setting() {
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreationDate() {
		return this.created;
	}

	public Date getModifiedDate() {
		return this.modified;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}