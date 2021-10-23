package com.IdeaBox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IdeaBox.models.sugestoes.FileEstudoViabilidade;

@Repository
public interface FileRepository extends JpaRepository<FileEstudoViabilidade, String>{

	
}
