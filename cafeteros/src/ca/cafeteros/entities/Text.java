package ca.cafeteros.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import ca.cafeteros.utilities.Logger;


/**
 * The persistent class for the text database table.
 * 
 */
@Entity
@Table(name="Texts")
@NamedQueries({
	@NamedQuery(name="Text.findAll", query="SELECT t FROM Text t"),
})

public class Text extends DbTable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "text_id_seq", sequenceName = "text_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_id_seq")
	@Column(name="id", updatable=false)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
/*
	@Column(name="\"groupId\"", nullable=false, unique=false)
	private Integer groupId;
	*/
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
/*
	@JoinColumn(name="\"text\"", nullable=false, unique=false)
	private String text;
*/
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"textsId\"")
	private TextReference textReference;
	
	@Column(name="\"text\"", nullable=false, unique=false)
	private String text;
	
	public Text() {
		super();
	}
	
	public Text(Logger log){
		super(log);
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		log.info("Setting text. text: " + text);
		
		if(text==null || text.isEmpty()){
			addError("text", "text is empty");
		}else{
			if(text.length() > 254){
				addError("text", "text is bigger than 254");
			}
		}
		
		this.text = text;
	}
	
	public static List<String> formatTextInList(String text){
		
		int size = 254;
		List<String> textInList = new ArrayList<String>();
		
		for (int start = 0; start < text.length(); start += size) {
			textInList.add(text.substring(start, Math.min(text.length(), start + size)));
	    }
		
		return textInList;
	}
}