package com.IdeaBox.models.sugestoes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import com.IdeaBox.models.usuarios.Colaborador;
import com.fasterxml.jackson.annotation.JsonFormat;

import ch.qos.logback.classic.pattern.Util;

@Entity
public class Sugestao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String texto;

	@ManyToOne
	@JoinColumn(name = "colaborador_id")
	private Colaborador colaborador;

	@Column
	private double classificacao;

	@Enumerated(EnumType.STRING)
	private Status_Sugestao status = Status_Sugestao.EM_ANALISE_RH;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@Column
	private int totalDeAvaliacoes;

	@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "estudo_viabilidade_id")
	private FileEstudoViabilidade estudoViabilidade;
	
	
	@Column
	@DateTimeFormat(fallbackPatterns ="dd.MM.yyyy")
	private String dataEnvio = LocalDate.now().format(com.IdeaBox.util.Util.formatarData());

	
	@ManyToMany(mappedBy = "sugestoesAvaliadas", cascade = CascadeType.REFRESH)
	private List<Colaborador> avaliadores;

	public Sugestao() {

	}

	public Sugestao(Integer id, Colaborador colaborador, Categoria categoria, String texto) {
		super();
		setColaborador(colaborador);
		setClassificacao(0);
		setTexto(texto);
		this.setTotalDeAvaliacoes(0);
		this.setId(id);
		setCategoria(categoria);
		avaliadores = new ArrayList<Colaborador>();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getTotalDeAvaliacoes() {
		return totalDeAvaliacoes;
	}

	public void setTotalDeAvaliacoes(int totalDeAvaliacoes) {
		this.totalDeAvaliacoes = totalDeAvaliacoes;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public double getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(double classificacao) {
		this.classificacao = classificacao;
	}

	public Status_Sugestao getStatus() {
		return status;
	}

	public void setStatus(Status_Sugestao status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDataEnvio() {
		return dataEnvio;
	}

	public List<Colaborador> getAvaliadores() {
		return avaliadores;
	}

	public void setAvaliadores(List<Colaborador> avaliadores) {
		this.avaliadores = avaliadores;
	}

	public FileEstudoViabilidade getEstudoViabilidade() {
		return estudoViabilidade;
	}

	public void setEstudoViabilidade(FileEstudoViabilidade estudoViabilidade) {
		this.estudoViabilidade = estudoViabilidade;
	}

	@Override
	public String toString() {
		return "Sugestao [id=" + id + ", texto=" + texto + ", colaborador=" + colaborador + ", classificacao="
				+ classificacao + ", status=" + status + ", categoria=" + categoria + ", totalDeAvaliacoes="
				+ totalDeAvaliacoes + ", dataEnvio=" + dataEnvio + "]";
	}
	


}
