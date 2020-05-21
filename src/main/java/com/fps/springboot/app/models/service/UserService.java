package com.fps.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fps.springboot.app.models.dao.IUsuarioDao;
import com.fps.springboot.app.models.entity.Role;
import com.fps.springboot.app.models.entity.Usuario;

@Service("userDetailsService")
public class UserService implements UserDetailsService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null)
		{
			logger.error("Error login: usuario no existe");
			throw new UsernameNotFoundException("username "+username+" no existe");
		}
		
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		for(Role role: usuario.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty())
		{
			logger.error("Error login: usuario "+username+" no tiene roles asignados");
			throw new UsernameNotFoundException("Error login: usuario '"+ username+ "' no tiene roles asignados");
		}

		return new User(usuario.getUsername(), usuario.getPassword(),usuario.isEnabled(), true,true,true, authorities);
	}

	
}
