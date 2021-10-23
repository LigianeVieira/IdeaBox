package com.IdeaBox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.IdeaBox.models.usuarios.Administrador;
@Repository
public interface AdminRepository extends CrudRepository<Administrador, String>{

}
