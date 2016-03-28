package ca.cafeteros.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManagerFactory;

import ca.cafeteros.utilities.Logger;
import ca.cafeteros.utilities.DB;
import ca.cafeteros.entities.ParameterValue;
import ca.cafeteros.entities.Detail;
import ca.cafeteros.entities.User;


public class Team extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String ADD_JSP_FILE = "/WEB-INF/view/pages/team/add.jsp";
	private final static String TEAM_JSP_FILE = "/WEB-INF/view/pages/team/team.jsp";
	private final static String PLAYERS_JSP_FILE = "/WEB-INF/view/pages/team/players.jsp";
	private final static String REGISTER_JSP_FILE = "/WEB-INF/view/pages/team/register.jsp";
	private final static String EDIT_JSP_FILE = "/WEB-INF/view/pages/team/edit.jsp";
	private final static String ADDTEAM_THANKYOU_PAGE = "/thankyou/addTeam";
	private final static String REGISTERUSER_THANKYOU_PAGE = "/thankyou/userRegisterdInTeam";
	
	private Logger log;
	private DB db;
	private EntityManagerFactory emfactory;
	private String[] pathParts;
	private ca.cafeteros.beans.Team teamBean;
	
	public void init(ServletConfig config) throws ServletException {
		
		log = (Logger)config.getServletContext().getAttribute("Logger");
		db = (DB)config.getServletContext().getAttribute("db");
		emfactory = (EntityManagerFactory)config.getServletContext().getAttribute("EntityManagerFactory");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Executing adding team servlet");
		
		pathParts = request.getPathInfo().split("/");
		String teamName = request.getPathInfo().substring(request.getPathInfo().lastIndexOf("/") + 1);
		
		ca.cafeteros.entities.Team team = db.getTeamFromName(teamName);		
		if(request.getAttribute("team") == null){
			request.setAttribute("team", team);
		}else{
			team = (ca.cafeteros.entities.Team)request.getAttribute("team");
		}
		
		teamBean = new ca.cafeteros.beans.Team(log, db, team);
		request.setAttribute("teamBean", teamBean);
		
		if(pathParts[1].equals("add")){
			request.getRequestDispatcher(ADD_JSP_FILE).forward(request, response);
		
		}else if(pathParts[1].equals(team.getName())){
			request.getRequestDispatcher(TEAM_JSP_FILE).forward(request, response);
		
		}else if(team.getName() != null && !team.getName().isEmpty()){
			
			if(pathParts[1].equals("players")){
				request.getRequestDispatcher(PLAYERS_JSP_FILE).forward(request, response);	
		
			} else if(pathParts[1].equals("register")){
				request.getRequestDispatcher(REGISTER_JSP_FILE).forward(request, response);
			
			} else if(pathParts[1].equals("edit")){
				request.getRequestDispatcher(EDIT_JSP_FILE).forward(request, response);
				
			}else{
				log.info("Visitor has requested and invalid URL. url: " + request.getPathInfo());
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			
		}else{
			log.info("Visitor has requested and invalid URL. url: " + request.getPathInfo());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("executing post function of servlet.");
		
		if(request.getParameter("submit") != null){
			
			if(request.getParameter("submit").equals("add"))
				add(request, response);
			
			else if(request.getParameter("submit").equals("enrollUser"))
				enroll(request, response);
			
			else{
				log.error("Visitor has send post information to wrong action servlet . submit: " + request.getParameter("submit"));
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
				
		}else if(request.getParameter("formName") != null){
			
			if (request.getParameter("formName").equals("changePlayerStatus"))
				changePlayerStatus(request, response);
			
			else
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			
		}else{
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Adding team to the club");
		
		//Integer descriptionId = db.addText(request.getParameter("teamDescription"));
		//Integer agreementId = db.addText(request.getParameter("teamEnrollmentAgreement"));
		//Integer groupId = db.getNextGroupIdInText();
		
		ca.cafeteros.entities.Team team = new ca.cafeteros.entities.Team(log);
		team.setName(request.getParameter("teamName"));
		team.setIntroduction(request.getParameter("teamIntroduction"));
		team.setDescription(request.getParameter("teamDescription"));
		team.setAgreement(request.getParameter("teamEnrollmentAgreement"));
		
		if(team.getErrors().size() > 0){
			request.setAttribute("team", team);
			doGet(request, response);
		}else{
			try{
				db.save(team);
				response.sendRedirect(request.getContextPath() + ADDTEAM_THANKYOU_PAGE);
				
			}catch(IOException ex){
				log.error(ex.getMessage());
				team.addError("addTeam", "An internal error ocurred while creating the new team");
				request.setAttribute("team", team);
				doGet(request, response);
			}
		}
	}

	private void enroll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.info("Enrolling user to a team");
		
		ParameterValue parameterValue = db.getParameterValue("player status in team", "requested");
		String teamName = request.getPathInfo().substring(request.getPathInfo().lastIndexOf("/") + 1);
		
		if(teamName.equals((String)request.getParameter("teamName"))){
			ca.cafeteros.entities.Team team = db.getTeamFromName(teamName);
			User user = db.getUserByEmail(request.getParameter("userEmail"));
			
			if(request.getParameter("acceptAgreement") != null && request.getParameter("acceptAgreement").equals("oui")){
				
				try{
					Detail detail = new Detail();
					detail.setTableaId(team.getId());
					detail.setTablebId(user.getId());
					detail.setParameterValue(parameterValue);
					db.save(detail);
					response.sendRedirect(request.getContextPath() + REGISTERUSER_THANKYOU_PAGE);
				}catch(Exception ex){
					request.setAttribute("registrationError", "An internal error ocurred while trying to submit your request");
					doGet(request, response);
				}
			}else{
				request.setAttribute("registrationError", "Please accept the enrollment agreement");
				doGet(request, response);
			}
		}else{
			log.error("form does not match with the url page. teamName in form: " + (String)request.getParameter("teamName") + " teamName in URL: " + teamName);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void changePlayerStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.info("Changing status of player in a team");
		
		String teamName = request.getPathInfo().substring(request.getPathInfo().lastIndexOf("/") + 1);
		String teamNameForm = (String)request.getParameter("teamName");
		ca.cafeteros.beans.SessionUser userBean = (ca.cafeteros.beans.SessionUser)request.getAttribute("sessionUser");
		ca.cafeteros.entities.Team team = db.getTeamFromName(teamName);
		User user = db.getUserByEmail(request.getParameter("playerEmail"));
		
		try{
			if(teamName.equals(teamNameForm) && userBean.isTeamManager(team)){
				
				
				List<Detail> playerEnrollmentStatus= db.getDetails("player status in team", team.getId(), user.getId());
				Detail playerStatus = playerEnrollmentStatus.get(0);
				ParameterValue newPlayerStatus = db.getParameterValue("player status in team", (String)request.getParameter("playerStatusInTeam"));
				
				EntityManager em = emfactory.createEntityManager();
				em.getTransaction().begin();
				Detail detail = em.createNamedQuery("Detail.findDetail", Detail.class).setParameter("name", "player status in team").setParameter("value", playerStatus.getParameterValue().getValue()).setParameter("tableaId", team.getId()).setParameter("tablebId", user.getId()).getSingleResult();
				detail.setParameterValue(newPlayerStatus);
				em.getTransaction().commit();
	    		em.clear();
	    		em.close();
	    		response.sendRedirect(request.getRequestURI());
					
			}else{
				log.error("Something weird happend as if someone were coding the form before send it. A hacke??");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}catch(Exception ex){
			log.error("Internal error while persisting the change in database");
			log.error(ex.getMessage());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
