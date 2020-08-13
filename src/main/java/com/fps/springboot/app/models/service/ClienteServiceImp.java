package com.fps.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void save(Cliente cliente) {
		em.save(cliente);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
