package ca.cafeteros.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


/**
 * The persistent class for the text_references database table.
 * 
 */
@Entity
@Table(name="text_references")
@NamedQuery(name="TextReference.findAll", query="SELECT t FROM TextReference t")
public class TextReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "texts_id_seq", sequenceName = "texts_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "texts_id_seq")
	@Column(name="id", updatable=false, unique=true)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"field\"", nullable=true, unique=false)
	private String field;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"table\"", nullable=true, unique=false)
	private String table;
	
	@OneToMany(mappedBy="textReference")
	private List<Text> texts;

	public TextReference() {
		texts = new ArrayList<Text>();
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

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
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
	
	public void setText(String text){
		
		List<String> lines = Text.formatTextInList(text);
		
		for(String line : lines){
			Text textEntity = new Text();
			textEntity.setText(line);
			texts.add(textEntity);
		}
	}
}