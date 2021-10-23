package com.IdeaBox.repository;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.kotlin.CoroutineCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.usuarios.Administrador;
import com.IdeaBox.models.usuarios.Colaborador;
import com.IdeaBox.models.usuarios.Gerente;
@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{
	Colaborador findById(long id);
	@Query("select i from Colaborador i where i.email = :email")
	Colaborador findByEmail(String email);
	
	@Query("select i from Gerente i where i.email = :email")
	Gerente findByEmailG(String email);

	
	@Query("select u from Colaborador u where u.login = :login and u.senha = :senha")
	Colaborador findLogin(String login, String senha);
	
	@Query("select u from Administrador u where u.login = :login and u.senha = :senha")
	Administrador findByLoginA(String login, String senha);

	
	@Query(value="SELECT avaliadores_id FROM ideabox.colaborador_sugestoes_avaliadas where sugestoes_avaliadas_id = :sugestoes_avaliadas_id and avaliadores_id = :avaliadores_id", nativeQuery = true)
	List<Long>findByAvaliacao(long sugestoes_avaliadas_id, long avaliadores_id);
	

}


