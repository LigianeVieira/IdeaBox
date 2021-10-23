package com.IdeaBox.models.usuarios;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.IdeaBox.models.cargos.Cargo;

@Entity
public class Gerente extends Colaborador {

	private static final long serialVersionUID = 1L;

	public Gerente(Integer id, String nome, String CPF, Cargo cargo, String login, String senha, String email) {
		super(id, nome, CPF, cargo, login, senha, email);
	}

	public Gerente() {

	}

}
