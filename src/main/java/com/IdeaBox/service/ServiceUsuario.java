package com.IdeaBox.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IdeaBox.exceptions.CriptoExistException;
import com.IdeaBox.exceptions.EmailExistException;
import com.IdeaBox.exceptions.NomeCargoExistsException;
import com.IdeaBox.exceptions.ServiceExce;
import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.usuarios.Administrador;
import com.IdeaBox.models.usuarios.Colaborador;
import com.IdeaBox.models.usuarios.Gerente;
import com.IdeaBox.repository.CargoRepository;
import com.IdeaBox.repository.ColaboradorRepository;
import com.IdeaBox.repository.GerenteRepository;
import com.IdeaBox.util.Util;

@Service
public class ServiceUsuario {
	@Autowired
	private ColaboradorRepository cr;
	
	@Autowired
	private GerenteRepository gr;
	
	@Autowired
	private CargoRepository cgr;
	
	public void salvarColaborador(Colaborador colaborador) throws Exception {
		try {
			if(cr.findByEmail(colaborador.getEmail()) != null) {
				throw new EmailExistException("Esse email já está cadastro para: " + colaborador.getEmail());
			}
			
			colaborador.setSenha(Util.md5(colaborador.getSenha()));
			
		} catch (NoSuchAlgorithmException e) {
			throw new CriptoExistException("Erro na criptografia na senha");
		}
		
		cr.save(colaborador);
	}
	
	public void salvarCargos(Cargo cargo) {
		try {
			if(cgr.findNome(cargo.getNome()) != null) {
				throw new NomeCargoExistsException("Este cargo ja existe!.");
			}
		} catch(NomeCargoExistsException e) {
			System.out.println(e.getMessage());
		}
		cgr.save(cargo);
	}
	
	public void salvarGerente(Gerente gerente) throws Exception {
		try {
			if(cr.findByEmailG(gerente.getEmail()) != null) {
				throw new EmailExistException("Esse email já está cadastro para: " + gerente.getEmail());
			}
			
			gerente.setSenha(Util.md5(gerente.getSenha()));
			
		} catch (NoSuchAlgorithmException e) {
			throw new CriptoExistException("Erro na criptografia na senha");
		}
		
		gr.save(gerente);
	}

	
	public Colaborador loginColaborador(String login, String senha) throws ServiceExce{
		
		Colaborador colaboradorLogin = cr.findLogin(login, senha);
		return colaboradorLogin;
		
	}
	
	public Administrador loginAdm(String login, String senha) throws ServiceExce{
		Administrador admLogin = cr.findByLoginA(login, senha);
		return admLogin;
	}
	
	public Gerente loginGerente(String login, String senha) throws ServiceExce{
		Gerente gerenteLogin = gr.findByLogin(login,senha);
		return gerenteLogin;
	}
}
