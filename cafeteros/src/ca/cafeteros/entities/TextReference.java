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
	@Column(name="id", nullable=false, updatable=false, unique=true)
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	
	@Column(name="\"field\"", nullable=true, unique=false)
	private String field;
	
	@Column(name="\"table\"", nullable=true, unique=false)
	private String table;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="textReference", orphanRemoval = true)
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
			textEntity.setTextReference(this);
			texts.add(textEntity);
		}
	}
	
	public String getText(){
		String text = "";
		
		for(Text line : texts){
			text += line.getText();
		}
		
		return text;
	}
	
	public static void main(String[] args){
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("cafeteros");
		
		try{
    		
    		EntityManager em = emfactory.createEntityManager();	
    		
    		try{
    			em.getTransaction().begin();
    			TextReference testText = new TextReference();
    			testText.setField("none, this is a test");
    			testText.setTable("none, this is a test");
    			testText.setText("1234askd;jf ;als;lkasjd ;sdfkjfdljas alflhsadf lahsdfuchuq qpeirupqoi q[omvxzcnvm[ qiuewpr  nbcnvbuereui c,nmbvc,x oqieyroqy bnbcxzncbv ekqwjjq;ew qwrhlerh lqwhreqe qfsafhdfyosidu dsfoiuyidfia foaudsfy brneqbe, ttr vy yetbvymretvye  yetv ermtyver t emrnbvtymer vertyvntr yv tey gfin");
    			em.persist(testText);
				em.getTransaction().commit();
				
			}catch(Exception ex){
				System.out.println("Error while persisting the new team in the database");
				//System.out.println(ex.getMessage());
			}finally{
				System.out.println("Closing the entity manager");
				em.clear();
				em.close();
			}
    		
    	}catch (IllegalStateException ex){
    		System.out.println("Can not create connection to the database because entity manager has been closed.");
    	}
		
		finally{
			emfactory.close();
		}
	}
}