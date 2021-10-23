package com.IdeaBox.models.sugestoes;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arquivos")
public class FileEstudoViabilidade {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nome;

	private String tipo;

	@Lob
	private byte[] data;

	private volatile transient Path filePath;

	private final String path = "aaa";
	
	@OneToOne
	@JoinColumn(name = "sugestao_id")
	private Sugestao sugestao;

	public FileEstudoViabilidade() {

	}

	public FileEstudoViabilidade(String nome, String tipo, byte[] data) {
		this.nome = nome;
		this.tipo = tipo;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public Sugestao getSugestao() {
		return sugestao;
	}

	public void setSugestao(Sugestao sugestao) {
		this.sugestao = sugestao;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Path toPath() {
		Path result = filePath;
		if (result == null) {
			synchronized (this) {
				result = filePath;
				if (result == null) {
					result = FileSystems.getDefault().getPath(path);
					filePath = result;
				}
			}
		}
		return result;
	}

}
