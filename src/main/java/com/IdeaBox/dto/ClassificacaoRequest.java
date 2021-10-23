package com.IdeaBox.dto;

public class ClassificacaoRequest {
	double classificacao;

	
	
	public ClassificacaoRequest(double classificacao) {
		this.classificacao = classificacao;
	}

	public ClassificacaoRequest() {
		
	}
	
	public double getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(double classificacao) {
		this.classificacao = classificacao;
	} 
	
	
}
