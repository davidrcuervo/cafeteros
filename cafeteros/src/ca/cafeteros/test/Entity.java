package ca.cafeteros.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ca.cafeteros.entities.*;

import java.util.*;


public class Entity {
	
	public static void main(String[] args){
		
		Entity entity = new Entity();
		//entity.createUser();
		entity.getUser(17);
	}
	
	public void createUser(){
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("cafeteros");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
				
		User user = new User();
		user.setEmail("davidrcuervo@gmail.com");
		user.setLName("David");
		user.setFName("Cuervo");
		user.setUsername("davidrcuervo");
		user.setPassword("86b4d07558aaafc1d468cb56efeb860cc1f994d9265feb03cda1653817585f8d");
		
		
		UserRole role = new UserRole();
		role.setUsername(user.getUsername());
		//role.setUserID(user.getId());
		role.setRole("player");
		user.addRole(role);
		
		entitymanager.persist(user);
		entitymanager.persist(role);
		
		entitymanager.getTransaction().commit();
		
		entitymanager.close();
		emfactory.close();
		
		System.out.println("User ID: " + user.getId());
		System.out.println("Created date: " + user.getCreatedDate());
		System.out.println("Last modified date: " + user.getModifiedDate());
		
	}
	
	public void getUser(Integer userID){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("cafeteros");
		EntityManager entitymanager = emfactory.createEntityManager();
		
		User user = entitymanager.find(User.class, userID);
		
		System.out.println("user ID = " + user.getId());
		System.out.println("user Name = " + user.getFName());
		System.out.println("user Last Name = " + user.getLName());
		System.out.println("user email = " + user.getEmail());
		System.out.println("user username = " + user.getUsername());
		System.out.println("user password = " + user.getPassword());
		System.out.println("user created date = " + user.getCreatedDate());
		System.out.println("user modified date = " + user.getModifiedDate());
		
		List<UserRole> roles = user.getUserRoles();
		
		for(UserRole role : roles){
			System.out.println("user role = " + role.getRole());
		}
		
		entitymanager.close();
		emfactory.close();
	}

}
