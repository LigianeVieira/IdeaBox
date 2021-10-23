package com.IdeaBox.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.IdeaBox.models.sugestoes.FileEstudoViabilidade;
import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.repository.FileRepository;
import com.IdeaBox.repository.SugestaoRepository;

@Service
public class FileStorageService {

	@Autowired
	private FileRepository fr;

	@Autowired
	private SugestaoRepository sr;
	
	public FileEstudoViabilidade store(MultipartFile file, long id) throws IOException {

		Sugestao sugestao = sr.findById(id);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileEstudoViabilidade File = new FileEstudoViabilidade(fileName, file.getContentType(), file.getBytes());
		sugestao.setEstudoViabilidade(File);
		sr.save(sugestao);
		File.setSugestao(sugestao);
		return fr.save(File);
	}
	
	public FileEstudoViabilidade getFile(String Id) {
		return fr.findById(Id).get();
	}
	
	public Stream<FileEstudoViabilidade> getAllFiles() {
		return fr.findAll().stream();
	}
}
