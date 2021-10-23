package com.IdeaBox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.repository.SugestaoRepository;


@Service
public class ServicePagination {
	@Autowired
	SugestaoRepository sr;

	public Page<Sugestao> findPaginated(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return  this.sr.findAllByStatus(pageable);
	}
}
