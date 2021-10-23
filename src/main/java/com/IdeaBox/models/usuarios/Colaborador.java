package com.IdeaBox.models.usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Target;
import org.springframework.transaction.annotation.Transactional;

import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.sugestoes.Sugestao;
@Entity
public class Colaborador extends Usuario{
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity=Sugestao.class, orphanRemoval = true)
	protected List <Sugestao> sugestoes;
	

	@ManyToMany(cascade=CascadeType.REFRESH)
    @JoinTable(name="COLABORADOR_SUGESTOES_AVALIADAS", joinColumns={@JoinColumn(referencedColumnName="ID")}
                                        , inverseJoinColumns={@JoinColumn(referencedColumnName="ID")}) 
	
	protected List <Sugestao> sugestoesAvaliadas;
		
	public Colaborador(Integer id, String nome, String CPF, Cargo cargo, String login, String senha, String email) {
		super(id, nome, CPF,  cargo, login, senha, email);
		
		
		setId(id);
		setNome(nome);
		setCpf(CPF);
	    setCargo(cargo);
		setLogin(login);
		setSenha(senha);
		setEmail(email); 
		setStatus(StatusColaborador.ATIVO);
		sugestoes = new ArrayList<Sugestao>();
		sugestoesAvaliadas = new ArrayList<Sugestao>();
	}
	
	public Colaborador() {
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public List<Sugestao> getSugestoes() {
		return sugestoes;
	}



	public void setSugestoes(List<Sugestao> sugestoes) {
		this.sugestoes = sugestoes;
	}
	public List<Sugestao> getSugestoesAvaliadas() {
		return sugestoesAvaliadas;
	}
	public void setSugestoesAvaliadas(List<Sugestao> sugestoesAvaliadas) {
		this.sugestoesAvaliadas = sugestoesAvaliadas;
	}

}
