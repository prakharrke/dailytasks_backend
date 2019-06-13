package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.DBCommunication;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import subtask.SubTask;
import task.OrdinaryTask;
import task.Task;
import user.User;

/**
 * Servlet implementation class GetMyTasks
 */
public class GetMyTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyTasks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String authHeader = request.getHeader("Authorization");
		String requestJWS = "";
		if(authHeader!= null && authHeader.contains("Bearer")) {
		requestJWS = authHeader.split("Bearer ")[1];
		}
		User user = null;
		String userName = "";
		
		// * JWS IS PRESENT
				/*
				 * CASE 1 :  HTTPSESSION HAS EXPIRED AND THE SESSION IS NEW SESSION -> REDIRECT TO LOGIN PAGE
				 * CASE 2 : SESSION HAS NOT EXPIRED AND USERSESSION ATTRIBUTE IS PRESENT -> GET USERSESSION -> VALIDATE THE TOKEN
				 */
				if(session.getAttribute("userSession") == null) {
					
					// * REDIRECT TO LOGIN PAGE
					response.setStatus(401);
					PrintWriter out = response.getWriter();
					out.println("");
				}else {
					
					user = (User) session.getAttribute("userSession");
					try {
						userName = user.getTokenCreator().validateJWTToken(requestJWS);
						session.setAttribute("userSession", user);
						
						// GET MY TASKS HERE
						DBCommunication dbCommunication = new DBCommunication();
						String tasks = dbCommunication.fetchTasks(user.getUserName());
						PrintWriter out = response.getWriter();
						out.println(tasks);
						
					} catch (AuthenticationException e) {
						response.setStatus(401);
						PrintWriter out = response.getWriter();
						out.print("Authentication Failed");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					
					
				}
	}

}
