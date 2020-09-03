package com.fps.springboot.app.auth.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fps.springboot.app.auth.service.JWTService;
import com.fps.springboot.app.auth.service.JWTServiceImpl;
import com.fps.springboot.app.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@CrossOrigin(origins = {"http://localhost:4200"})
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	
	

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,	JWTService jwtService) {
		this.authenticationManager = authenticationManager; //obtiene el autenticado
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST")); //cambia la ruta por defecto de login
		this.jwtService = jwtService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username != null && password != null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Paswword desde request parameter (form-data): " + password);
		}else
		{
			Usuario user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username desde request InputStream: " + username);
				logger.info("Paswword desde request InputStream: " + password);
			} catch (JsonParseException e) {
				
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);
		
		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token); // requerido 
		// convertir a json
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Inicio de sesion correcto, Bienvenido %s", ((User)authResult.getPrincipal()).getUsername()));
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("aplication/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticacion: Usuario o Contraseña incorrecto!");
		body.put("error",failed.getMessage());
		
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("aplication/json");
	}
	
	

}
