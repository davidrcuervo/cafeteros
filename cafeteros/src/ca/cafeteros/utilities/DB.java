package ca.cafeteros.utilities;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import ca.cafeteros.entities.*;

public class DB {
	
	private EntityManagerFactory emfactory;
    private Logger log;
    
    public DB(EntityManagerFactory emfactory){
    	log = new Logger();
    	this.emfactory = emfactory;
    }
    
    public DB(Logger log, EntityManagerFactory emfactory){
    	this.log = log;
    	this.emfactory = emfactory; 
    }
    
    public void save(DbTable table) throws IOException{
    	log.info("Persisting object in the database...");
    	   	
		if(emfactory != null){
			try{
				EntityManager em = emfactory.createEntityManager();
				
				try{
					em.getTransaction().begin();
					em.persist(table);
					em.getTransaction().commit();
					
					log.info("Object has been persisted succesfully");
				}catch(IllegalStateException ex){
					//if getTransaction invoked on a JTA enityt manager
					//if begin() isActive is true
					log.critical("Exception has thrown when trying to create entity manager, or getting transaction from entity manager, or begin has not been closed correctly");
					throw new IOException("It has not been possible to persist object in the database");
				}catch(RollbackException ex){
					log.error("Object has not been able to persist. Rollback process will be executed");
					try{
						em.getTransaction().rollback();
						log.debug("Object has been rolled back succesfully");
					}catch(IllegalStateException e){
						log.critical("Illegal State Exception when trying to rollback");
						throw new IOException("It has not been possible to persist object in the database");
					}catch(PersistenceException e){
						log.critical("Persistence exception when trying to persiste rollback");
						throw new IOException("It has not been possible to persist object in the database");
					}finally{
						em.close();
					}
				}
			}catch (IllegalStateException ex){
				
				log.critical("Exception has thrown when trying to create entity manager, or getting transaction from entity manager, or begin has not been closed correctly");
				throw new IOException("It has not been possible to persist object in the database");
			} 
		}else {
			log.critical("Entity manager factory is null");
			throw new IOException("The connection to database does not exist");
		}
    }
        
    public User getUserByEmail(String email) throws IOException{
    	log.info("Finding user by email. email: " + email);
    	
    	User user = null;
		String queryName = "User.findByEmail";
		
		if(emfactory == null){
			throw new IOException("Error while creating entity manager because entinty manager factory is null");
		}else{
			try{
				EntityManager em;
				em = emfactory.createEntityManager();
				
				try{
					TypedQuery<User> query = em.createNamedQuery(queryName, User.class);
					query.setParameter("email", email);
					user = query.getSingleResult();
					em.refresh(user);
				}catch (IllegalArgumentException ex){
					throw new IOException("It was not possible to find the parameter " + email.toUpperCase() + " in settings table. -> ERROR: " + ex.getMessage());
				}catch(NoResultException ex){
					throw new IOException("The setting: " + email.toUpperCase() + " does not exist in the user table");
				}catch(NonUniqueResultException ex){
					throw new IOException("The setting: " + email.toUpperCase() + " has more than one result");
				}finally{
					em.close();
				}
			}catch (IllegalArgumentException ex){
				throw new IOException("It was not possible to find the parameter " + email.toUpperCase() + " in settings table. -> ERROR: " + ex.getMessage());
			}
		}		
		return user;
    }

    public User getUserById(Integer userId){
    	log.info("Finding user by Id. id = " + userId);
    	
    	User user = new User();
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		user = em.find(User.class, userId);
    		em.clear();
    		em.close();	
    	}catch (Exception ex){
    		log.error("User by ID was not found");
    		log.error(ex.getMessage());
    	}
    	
    	return user;
    }
    
    public Integer getNextGroupIdInText(){
    	
    	Integer groupId = 0;
    	
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		groupId = (Integer)em.createQuery("select max(t.groupId) from Text t").getSingleResult();
    		groupId++;
    	}catch(Exception ex){
    		log.error("error while finding max groupId for text table");
    		log.error(ex.getMessage());
    		groupId = 0;
    	}
    	
    	return groupId;
    	 
    }
    /*
    public int addText(String text){
    	log.info("Adding new string to the text table. text: " + text);
    	Integer groupId = 0;
    	
    	try{
    		List<String> textList = Text.formatTextInList(text);
    		EntityManager em1 = emfactory.createEntityManager();
    		groupId = (Integer)em1.createQuery("select max(t.groupId) from Text t").getSingleResult();
    		groupId++;
    		log.debug("GroupId for new text is. groupId: " + groupId);
    		em1.clear();
    		em1.close();
    		
    		log.debug("Number of lines for the text. size: " + Integer.toString(textList.size()));
    		for(String line : textList){
    			log.debug("adding line of a text. line: " + line);	    			
    			Text dbText = new Text(log);
    			dbText.setText(line);
    			dbText.setGroupId(groupId);
    			
    			if(dbText.getErrors().size() > 0){
    				log.info("There are errors, then no persisting in db");
    			}else{
    				try{
        				this.save(dbText);
        			}catch(IOException ex){
        				log.error(ex.getMessage());
        				return 0;
        			}
    			}	
    		}
    		
    		return groupId;
    	}catch(Exception ex){
    		log.error(ex.getMessage());
    		return 0;
    	}
    }
*/
    public List<Team> getAllTeams(){
    	log.info("Getting all teams from the database");
    	List<Team> teams = new ArrayList<Team>();
    	
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		teams = em.createNamedQuery("Team.findAll", Team.class).getResultList();
    		em.clear();
    		em.close();	
    		
    	}catch(Exception ex){
    		log.error(ex.getMessage());
    	}
    	 return teams;
    }

    public Team getTeamFromName(String teamName){
    	log.info("Getting team from name. teamName: " + teamName);
    	Team team = new Team();
    	
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		team = em.createNamedQuery("Team.findByName", Team.class).setParameter("teamName", teamName).getSingleResult();
    		em.clear();
    		em.close();	
    	}catch(Exception ex){
    		log.error("Team was not found in the database");
    		log.error(ex.getMessage());
    	}
    	
    	return team;
    }

    public Parameter getParameterByName(String parameterName){
    	log.info("Finding parameter by name. name: \"" + parameterName + "\"");
    	
    	Parameter parameter = new Parameter();
    	
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		parameter = em.createNamedQuery("Parameter.findByName", Parameter.class).setParameter("parameterName", parameterName).getSingleResult();
    		em.clear();
    		em.close();
    	}catch(Exception ex){
    		log.error("Error while finding parameter");
    		log.error(ex.getMessage());
    	}
    	
    	return parameter;
    }
    
    public ParameterValue getParameterValue(String name, String value){
    	log.info("Getting parameter and value. parameterName: \"" + name + "\" parameterValue: \"" + value + "\"");
    	
    	ParameterValue parameterValue = new ParameterValue();
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		parameterValue = em.createNamedQuery("ParameterValue.findByValueAndName", ParameterValue.class).setParameter("name", name).setParameter("value", value).getSingleResult();
    		em.clear();
    		em.close();	
    		
    	}catch(Exception ex){
    		log.error("Parameter value not found");
    		log.error(ex.getMessage());
    	}
    	return parameterValue;
    }
    
    public Detail getDetail(String parameterName, String valueName, Integer tableaId, Integer tablebId){
    	log.info("Getting detail. parameter name: \"" + parameterName + "\", value name: \"" + valueName + "\", table A Id: \"" + Integer.toString(tableaId) + "\", table B Id: " + tablebId + "\"");
    	
    	Detail detail= new Detail();
    	
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		detail = em.createNamedQuery("Detail.findDetail", Detail.class).setParameter("name", parameterName).setParameter("value", valueName).setParameter("tableaId", tableaId).setParameter("tablebId", tablebId).getSingleResult();
    		em.clear();
    		em.close();
    	}catch (Exception ex){
    		log.error("Parameter value not found");
    		log.error(ex.getMessage());
    	}
    	return detail;
    }
    
    public List<Detail> getDetails(String parameterName, String valueName, Integer tableaId){
    	log.info("Getting details from table by parameter name: \"" + parameterName + "\", value name: \"" + valueName + "\", Id of table A: \"" + tableaId + "\"");
    	
    	List<Detail> details = new ArrayList<Detail>();
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		details = em.createNamedQuery("Detail.findByParameterValueAndTableaId", Detail.class).setParameter("name", parameterName).setParameter("value", valueName).setParameter("tableaId", tableaId).getResultList();
    		em.clear();
    		em.close();	
    		return details;
    	}catch(Exception ex){
    		log.error("Parameter value not found");
    		log.error(ex.getMessage());
    	}
    	return details;
    }
    
    public List<Detail> getDetails(String parameterName, Integer tableaId, Integer tablebId){
    	log.info("Getting details from table by parameter name: \"" + parameterName + "\", table A Id: \"" + Integer.toString(tableaId) + "\", Id of table B: \"" + tablebId + "\"");
    	
    	List<Detail> details = new ArrayList<Detail>();
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		details = em.createNamedQuery("Detail.findByTableaIdAndTablebId", Detail.class).setParameter("name", parameterName).setParameter("tableaId", tableaId).setParameter("tablebId", tablebId).getResultList();
    		em.clear();
    		em.close();	
    		return details;
    	}catch(Exception ex){
    		log.error("Parameter value not found");
    		log.error(ex.getMessage());
    	}
    	return details;
    	
    }

    public List<Detail> getDetailsByParameter(String parameterName, Integer tableaId){
    	log.info("Getting details from table by paramenter, parameter name: \"" + parameterName + "\", tableaId: \"" + tableaId + "\"");
    	
    	List<Detail> details = new ArrayList<Detail>();
    	try{
    		EntityManager em = emfactory.createEntityManager();
    		details = em.createNamedQuery("Detail.findParameterByNameAndTableaId", Detail.class).setParameter("name", parameterName).setParameter("tableaId", tableaId).getResultList();
    		em.clear();
    		em.close();	
    		return details;
    	}catch(Exception ex){
    		log.error("Parameter value not found");
    		log.error(ex.getMessage());
    	}
    	return details;
    	
    }
}
