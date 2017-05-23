package ca.cafeteros.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;



@Entity
@Table(name="detail_indexes")
@NamedQuery(name="DetailIndex.findAll", query="SELECT d FROM DetailIndex d")
public class DetailIndex extends DbTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "detail_indexes_id_seq", sequenceName = "detail_indexes_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detail_indexes_id_seq")
	@Column(name="id", updatable=false, unique=true)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="detailIndex", orphanRemoval = true)
	private List<Detail> details = new ArrayList<Detail>();
	
	@Column(name="\"table\"", nullable=false, unique=true)
	private String table;

	public DetailIndex() {
		
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreated() {
		return this.created;
	}

	public Date getModified() {
		return this.modified;
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	public List<Detail> getDetails(){
		return details;
	}
}