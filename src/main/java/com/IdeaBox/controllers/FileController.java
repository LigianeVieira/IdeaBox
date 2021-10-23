package com.IdeaBox.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.IdeaBox.models.sugestoes.FileEstudoViabilidade;
import com.IdeaBox.models.sugestoes.ResponseFile;
import com.IdeaBox.models.sugestoes.ResponseMessage;
import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.repository.SugestaoRepository;
import com.IdeaBox.service.FileStorageService;

@Controller
public class FileController {

	@Autowired
	private FileStorageService storageService;
	
	@Autowired
	private SugestaoRepository sr;

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") long id) {
		String message = "";

		try {
			storageService.store(file, id);
			message = "Arquivo enviado com sucesso: " + file.getOriginalFilename();
			return "redirect:/pendentes";
		} catch (Exception e) {
			message = "Não foi possivel enviar o arquivo: " + file.getOriginalFilename() + "!";
			e.printStackTrace();
			return "redirect:/pendentes";
		}
	}

	

	@GetMapping("/arquivo/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileEstudoViabilidade file = storageService.getFile(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getNome() + "\"")
				.body(file.getData());
	}
	@RequestMapping("/ver")
	public String verAquivoSugestao(long id) {
	Sugestao sugestao = sr.findById(id);
	FileEstudoViabilidade file = sugestao.getEstudoViabilidade();
	return "redirect:/arquivo/" + file.getId();
	}
	
}
