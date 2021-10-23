package com.IdeaBox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.models.usuarios.Administrador;
import com.IdeaBox.models.usuarios.Colaborador;
import com.IdeaBox.models.usuarios.Gerente;

public interface SugestaoRepository extends CrudRepository<Sugestao, String>{
	
	Iterable<Sugestao> findByColaborador(Colaborador colaborador);
	
	@Query(value = "SELECT * FROM sugestao WHERE status != 'EM_ANALISE_RH' and status <>'REPROVADO_PELO_RH' ", nativeQuery = true)
	Page<Sugestao> findAllByStatus(Pageable pageable);

	@Query(value = "SELECT * FROM sugestao WHERE status = 'EM_ANALISE_RH'", nativeQuery = true)
	Iterable<Sugestao> findAllInAnalise();
	
	@Query(value = "SELECT * FROM sugestao WHERE status = 'EM_ANALISE_GERENCIA'", nativeQuery = true)
	Iterable<Sugestao> findAllInAnaliseG();
	
	@Query(value = "SELECT * FROM sugestao where classificacao = (select max(classificacao) from sugestao) AND total_de_avaliacoes >= (SELECT floor(AVG(total_de_avaliacoes)) from sugestao) and status <> 'ARQUIVADO' and status <> 'TOP_TREND';", nativeQuery = true)
	Iterable<Sugestao> findTop();
	
	Sugestao findById(long id);

	@Query(value = "SELECT * FROM sugestao WHERE status = 'TOP_TREND'", nativeQuery = true)
	Iterable<Sugestao> findAllInTopTrend();
}
