package com.IdeaBox.models.usuarios;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.IdeaBox.models.cargos.Cargo;
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@Entity
public class Administrador extends Usuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Administrador() {
		
	}

	public Administrador(long id, String nome, String cpf, Cargo cargo, String login, String senha, String email) {
		super(id, nome, cpf, cargo, login, senha, email);
		// TODO Auto-generated constructor stub
	}
	
	
}
