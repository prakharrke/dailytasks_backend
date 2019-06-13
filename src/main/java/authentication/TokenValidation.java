package authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import net.sf.json.JSONObject;
import user.User;

/**
 * Servlet implementation class TokenValidation
 */
public class TokenValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TokenValidation() {
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
				JSONObject responseObject = new JSONObject();
				responseObject.put("username", user.getUserName());
				responseObject.put("role", user.getRole());
				responseObject.put("designation", user.getDesignation());
				responseObject.put("name", user.getName());
				PrintWriter out = response.getWriter();
				out.println(new Gson().toJson(responseObject));
			} catch (AuthenticationException e) {
				response.setStatus(401);
				PrintWriter out = response.getWriter();
				out.print("Authentication Failed");
			}			
			
			
		}
	}

}
