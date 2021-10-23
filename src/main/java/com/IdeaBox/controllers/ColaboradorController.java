package com.IdeaBox.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.models.usuarios.Colaborador;
import com.IdeaBox.repository.CargoRepository;
import com.IdeaBox.repository.ColaboradorRepository;
import com.IdeaBox.repository.SugestaoRepository;
import com.IdeaBox.service.ServiceUsuario;

@Controller
public class ColaboradorController {
	@Autowired
	private ColaboradorRepository cr;

	@Autowired
	private SugestaoRepository sr;

	@Autowired
	private CargoRepository crg;
	
	@Autowired
	private ServiceUsuario su;



	@RequestMapping("/deletar")
	public String deletarColaborador(long Id) {
		Colaborador colaborador = cr.findById(Id);
		cr.delete(colaborador);
		return "redirect:/colaboradores";
	}
	
	@PostMapping("/editarTudo")
	public String editarTudo(@RequestParam long id, @RequestParam("nome") String nome, 
			@RequestParam long cargoId, @RequestParam("login") String login, @RequestParam("email") String email) {
		
		Cargo cargo1 = crg.findById(cargoId);
		Colaborador colaborador = cr.findById(id);
		colaborador.setNome(nome);
		colaborador.setCargo(cargo1);
		colaborador.setLogin(login);
		colaborador.setEmail(email);
		
		cr.save(colaborador);
		crg.save(cargo1);
		
		return "redirect:/colaboradores";
	}
}
