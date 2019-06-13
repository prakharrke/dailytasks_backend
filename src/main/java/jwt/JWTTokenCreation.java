package jwt;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import user.User;

public class JWTTokenCreation {

	Key key = null;
	Date expirationDate = null;
	User user = null;
	public JWTTokenCreation(User user) {
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		long currentMillis = System.currentTimeMillis();
		long expirationMillis = currentMillis + (3600000 *  2);
		this.expirationDate = new Date(expirationMillis);
		this.user = user;
	}
	
	public String createJWTToken(String username) {
		Map<String, Object>headers = new HashMap<String, Object>();
		headers.put("designation", this.user.getDesignation());
		headers.put("role", this.user.getRole());
		headers.put("name", this.user.getName());
		String jws = Jwts.builder().setSubject(username).setHeader(headers).signWith(key).setExpiration(this.expirationDate).compact();
		
		return jws;
	}
	
	public String validateJWTToken(String jwsString) throws AuthenticationException {
		Jws<Claims> jws;
		try {
		jws = Jwts.parser()        
			    .setSigningKey(this.key)         
			    .parseClaimsJws(jwsString);
		Claims claims = jws.getBody();
		Header h = jws.getHeader();
		String role = (String) h.get("role");
		String username = claims.getSubject();
		this.user.setDesignation(h.get("designation").toString())
		.setUserName(username)
		.setName(h.get("name").toString())
		.setRole(h.get("role").toString());
		return username;
		}catch(JwtException e) {
			
			throw new AuthenticationException();
		}
		
		
	}
}
