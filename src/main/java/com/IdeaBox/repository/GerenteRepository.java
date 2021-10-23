package com.IdeaBox.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.IdeaBox.models.usuarios.Gerente;

public interface GerenteRepository extends CrudRepository<Gerente, String>{
	
	@Query("select u from Gerente u where u.login = :login and u.senha = :senha")
	Gerente findByLogin(String login, String senha);
	
	@Query(value="SELECT avaliadores_id FROM ideabox.colaborador_sugestoes_avaliadas where sugestoes_avaliadas_id = :sugestoes_avaliadas_id and avaliadores_id = :avaliadores_id", nativeQuery = true)
	List<Long>findByAvaliacao(long sugestoes_avaliadas_id, long avaliadores_id);
}
