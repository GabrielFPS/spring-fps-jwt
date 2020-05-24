package com.fps.springboot.app.controllers;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fps.springboot.app.models.service.ClienteServiceImp;

@Controller
@RestController
public class ClienteController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ClienteServiceImp clienteService;
	
	@GetMapping("/api/index")
	@Secured("ROLE_USER")
	public HashMap<String, Object> index()
	{
		HashMap<String, Object> map = new HashMap<>();
		map.put("clientes", clienteService.findAll());
		return map;
	}	

	
	
}
