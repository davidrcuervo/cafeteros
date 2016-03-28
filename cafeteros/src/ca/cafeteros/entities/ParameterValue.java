package ca.cafeteros.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the parameter_values database table.
 * 
 */
@Entity
@Table(name="parameter_values")
@NamedQueries({
	@NamedQuery(name="ParameterValue.findAll", query="SELECT p FROM ParameterValue p"),
	//@NamedQuery(name="ParameterValue.findByValueAndName", query="SELECT v FROM ParameterValue v JOIN v.parameter p WHERE v.value = :value AND p.name = :name")
	@NamedQuery(name="ParameterValue.findByValueAndName", query="SELECT v FROM ParameterValue v WHERE v.value = :value AND v.parameter.name = :name")
})
public class ParameterValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "parameter_values_id_seq", sequenceName = "parameter_values_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameter_values_id_seq")
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
/*
	@Column(name="\"parameterId\"", nullable=false, unique=false)
	private Integer parameterId;
	*/
	@Column(name="\"value\"", nullable=false, unique=false)
	private String value;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"parameterId\"")
	private Parameter parameter;
	
	@OneToMany( mappedBy = "parameterValue")
	private List<Detail> details;

	public ParameterValue() {}

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
/*
	public Integer getParameterId() {
		return this.parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}
*/
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}