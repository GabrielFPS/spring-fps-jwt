package com.fps.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fps.springboot.app.models.dao.IClienteDao;
import com.fps.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImp implements IClienteService {

	@Autowired
	private IClienteDao em;
	
	@Override
	public List<Cliente> findAll() {	
		return (List<Cliente>) em.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		
		return em.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		em.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		
		return em.findById(id).orElse(null);
	}
}
