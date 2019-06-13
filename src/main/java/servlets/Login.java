package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import authentication.Authenticate;
import net.sf.json.JSONObject;
import user.User;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		// TODO Auto-generated method stub
		
		// * Authenticate User 
		String userDetails = request.getParameter("userDetails");
		JSONObject userDetailsObject = new Gson().fromJson(userDetails, JSONObject.class);
		Authenticate auth = new Authenticate();
		try {
			
			User user = auth.getUserVerificationDetails(userDetailsObject);
			String jwtToken = user.getTokenCreator().createJWTToken(user.getUserName());
			JSONObject responseObject = new JSONObject();
			HttpSession session = request.getSession();
			session.setAttribute("userSession", user);
			
			responseObject.put("jwt", jwtToken);
			responseObject.put("username", user.getUserName());
			responseObject.put("role", user.getRole());
			responseObject.put("designation", user.getDesignation());
			responseObject.put("name", user.getName());
			PrintWriter out = response.getWriter();
			out.println(new Gson().toJson(responseObject));
			
			
		} catch (ClassNotFoundException e) {
			
			response.setStatus(401);
			PrintWriter out = response.getWriter();
			out.print("Authentication Failed");
			
		} catch (SQLException e) {
			response.setStatus(401);
			PrintWriter out = response.getWriter();
			out.print("Authentication Failed");
		}
		
	}

}
