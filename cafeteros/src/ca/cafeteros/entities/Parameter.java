package ca.cafeteros.entities;

import java.io.Serializable;
import java.util.List;
//import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the parameters database table.
 * 
 */
@Entity
@Table(name="parameters")
@NamedQueries({
	@NamedQuery(name="Parameter.findAll", query="SELECT p FROM Parameter p"),
	@NamedQuery(name="Parameter.findByName", query="SELECT p FROM Parameter p WHERE p.name = :parameterName")
})

public class Parameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "parameter_id_seq", sequenceName = "parameter_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"description\"", nullable=true, unique=false)
	private String description;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"name\"", nullable=false, unique=true)
	private String name;
	
	@Column(name="\"tableNameA\"", nullable=false, unique=false)
	private String tableNameA;
	
	@Column(name="\"tableNameB\"", nullable=true, unique=false)
	private String tableNameB;
	
	@OneToMany( mappedBy = "parameter")
	private List<ParameterValue> values;

	public Parameter() {
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreated() {
		return this.created;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModified() {
		return this.modified;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTableNameA() {
		return this.tableNameA;
	}

	public void setTableNameA(String tableNameA) {
		this.tableNameA = tableNameA;
	}
	
	public String getTableNameB() {
		return this.tableNameB;
	}

	public void setTableNameB(String tableNameB) {
		this.name = tableNameB;
	}
	
	public List<ParameterValue> getValues(){
		return this.values;
	}
}