package com.IdeaBox.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.IdeaBox.models.cargos.Cargo;

public interface CargoRepository extends CrudRepository<Cargo, String>{
	
	@Query(value = "select nome from Cargo where nome=?", nativeQuery = true)
	Cargo findNome(String nome);
	
	@Query(value = "select * from Cargo where nome <> 'Administrador'", nativeQuery = true)
	Iterable<Cargo> findAllexceptAdm();
	
	@Query(value = "select * from Cargo where nome <> 'Administrador' and nome <> 'Gerente' ", nativeQuery = true)
	Iterable<Cargo> findAllexceptGerente();
	
	@Query(value = "select * from Cargo", nativeQuery = true)
	Iterable<Cargo> findAll();
	
	Cargo findById(long id);
		
	
}
