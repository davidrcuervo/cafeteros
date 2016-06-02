package ca.cafeteros.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the details database table.
 * 
 */
@Entity
@Table(name="details")
@NamedQueries({
	@NamedQuery(name="Detail.findAll", query="SELECT d FROM Detail d"),
	@NamedQuery(name="Detail.findByTableaId", query="SELECT d FROM Detail d WHERE d.tableaId = :tableaId"),
	@NamedQuery(name="Detail.findByParameterValueAndTableaId", query="SELECT d FROM Detail d WHERE d.parameterValue.parameter.name = :name AND d.parameterValue.value = :value AND d.tableaId = :tableaId"),
	@NamedQuery(name="Detail.findByTableaIdAndTablebId", query="SELECT d FROM Detail d WHERE d.parameterValue.parameter.name = :name AND d.tableaId = :tableaId AND d.tablebId = :tablebId"),
	@NamedQuery(name="Detail.findParameterByNameAndTableaId", query="SELECT d FROM Detail d WHERE d.parameterValue.parameter.name = :name AND d.tableaId = :tableaId"),
	@NamedQuery(name="Detail.findDetail", query="SELECT d FROM Detail d WHERE d.parameterValue.parameter.name = :name AND d.parameterValue.value = :value AND d.tableaId = :tableaId AND d.tablebId = :tablebId"),
	
})

public class Detail extends DbTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "details_id_seq", sequenceName = "details_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "details_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;

	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@Column(name="\"tableaId\"")
	private Integer tableaId;

	@Column(name="\"tablebId\"")
	private Integer tablebId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"parameter_valueId\"")
	private ParameterValue parameterValue;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"index\"")
	private DetailIndex detailIndex;

	public Detail() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public ParameterValue getParameterValue() {
		return this.parameterValue;
	}
	/*
	public void setParameterValueId(Integer parameterValueId) {
		this.parameterValueId = parameterValueId;
	}
*/
	public Integer getTableaId() {
		return this.tableaId;
	}

	public void setTableaId(Integer tableaId) {
		this.tableaId = tableaId;
	}

	public Integer getTablebId() {
		return this.tablebId;
	}

	public void setTablebId(Integer tablebId) {
		this.tablebId = tablebId;
	}
	
	public void setParameterValue(ParameterValue parameterValue){
		this.parameterValue = parameterValue;
	}
	
	public void setDetailIndex(DetailIndex detailIndex){
		this.detailIndex = detailIndex;
	}

}