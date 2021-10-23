package com.IdeaBox.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.IdeaBox.exceptions.ServiceExce;
import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.sugestoes.Sugestao;
import com.IdeaBox.models.usuarios.Administrador;
import com.IdeaBox.models.usuarios.Colaborador;
import com.IdeaBox.models.usuarios.Gerente;
import com.IdeaBox.repository.CargoRepository;
import com.IdeaBox.repository.ColaboradorRepository;
import com.IdeaBox.repository.GerenteRepository;
import com.IdeaBox.repository.SugestaoRepository;
import com.IdeaBox.service.ServicePagination;
import com.IdeaBox.service.ServiceUsuario;
import com.IdeaBox.util.Util;

@Controller
public class UsuarioController {

	@Autowired
	private ColaboradorRepository cr;

	@Autowired
	private GerenteRepository gr;
	
	@Autowired
	private SugestaoRepository sr;

	@Autowired
	private ServiceUsuario su;

	@Autowired
	private CargoRepository crg;

	@Autowired
	private  ServicePagination sp;

	@GetMapping("/timeline/{pageNumber}")
	public   ModelAndView feed(HttpSession session, @PathVariable(value = "pageNumber") int pageNumber) {
		if (session.getAttribute("colaboradorLogado") != null || session.getAttribute("gerenteLogado") != null) {
			int pageSize = 5;
			ModelAndView mv = new ModelAndView("feed");
			Page<Sugestao> page = sp.findPaginated(pageNumber, pageSize);
			List<Sugestao> sugestoes = page.getContent();
			mv.addObject("currentPage", pageNumber);
			mv.addObject("totalPages", page.getTotalPages());
			mv.addObject("totalItems", page.getTotalElements());
			mv.addObject("sugestoes", sugestoes);
			return mv;
		} else {
			return loginGet();
		}
	}
	
	@RequestMapping(value = "/timeline/1", method = RequestMethod.POST)
	public ModelAndView form(Sugestao sugestao, HttpSession session) {
		ModelAndView mv = feed(session, 1);
		if (session.getAttribute("colaboradorLogado") != null) {
			Colaborador colaborador = (Colaborador) session.getAttribute("colaboradorLogado");
			sugestao.setColaborador(colaborador);
			
			colaborador.getSugestoes().add(sugestao);
			cr.save(colaborador);
			colaborador.getSugestoes().clear();
		} else {
			Gerente gerente = (Gerente) session.getAttribute("gerenteLogado");
			sugestao.setColaborador(gerente);
			
			gerente.getSugestoes().add(sugestao);
			gr.save(gerente);
			gerente.getSugestoes().clear();
		}
		mv.addObject("msg", "Sugestão enviada com sucesso.");
		return mv;
	}

	@GetMapping("/erroLogin")
	public ModelAndView error() {
		ModelAndView mv = new ModelAndView("naoLogado");
		return mv;
	}

	@PostMapping("/login")
	public ModelAndView login(Colaborador colaborador, Gerente gerente, Administrador adm, BindingResult br,
			HttpSession session) throws NoSuchAlgorithmException, ServiceExce {
		ModelAndView mv = new ModelAndView();
		mv.addObject("colaborador", new Colaborador());
		if (br.hasErrors()) {
			mv.setViewName("login");
		}
		Colaborador colaboradorLogin = su.loginColaborador(colaborador.getLogin(), Util.md5(colaborador.getSenha()));
		Administrador administradorLogin = su.loginAdm(adm.getLogin(), adm.getSenha());
		Gerente gerenteLogin = (Gerente) su.loginGerente(gerente.getLogin(), Util.md5(gerente.getSenha()));
		if (colaboradorLogin == null && administradorLogin == null && gerenteLogin == null) {
			mv.addObject("msg", "Usuario não encontrado tente novamente");
		}
		if (gerenteLogin instanceof Gerente) {
			session.setAttribute("gerenteLogado", gerenteLogin);
			return perfilGet(session);
		} else if (colaboradorLogin != null) {
			session.setAttribute("colaboradorLogado", colaboradorLogin);
			return perfilGet(session);
		} else if (administradorLogin != null) {
			session.setAttribute("AdmLogado", administradorLogin);
			return perfilGet(session);
		}

		return mv;

	}

	@GetMapping("/login")
	public static ModelAndView loginGet() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("colaborador", new Colaborador());
		mv.addObject("gerente", new Gerente());
		return mv;
	}

	@GetMapping("/")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("index");
		if (session.getAttribute("colaboradorLogado") != null || session.getAttribute("AdmLogado") != null
				|| session.getAttribute("gerenteLogado") != null) {
			return mv;
		}
		// AdmLogado
		else {
			mv.addObject("logadoexce", "Logue para acessar as funcionalidades do sistema.");
			return mv;
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/profile")
	public ModelAndView perfilGet(HttpSession session) {
		if (session.getAttribute("colaboradorLogado") != null) {
			Colaborador colaborador = (Colaborador) session.getAttribute("colaboradorLogado");
			ModelAndView mv = new ModelAndView("colaborador/profile");
			mv.addObject("colaborador", colaborador);
			Iterable<Sugestao> sugestoes = sr.findByColaborador(colaborador);
			mv.addObject("sugestoes", sugestoes);
			return mv;
		}

		else if (session.getAttribute("AdmLogado") != null) {
			Administrador administrador = (Administrador) session.getAttribute("AdmLogado");
			ModelAndView mv = new ModelAndView("colaborador/profileadm");
			Cargo cargo = administrador.getCargo();
			mv.addObject("administrador", administrador);
			mv.addObject("cargo", cargo);
			Iterable<Sugestao> topSugestoes = sr.findTop();
			mv.addObject("sugestoes", topSugestoes);
			return mv;
		} else if (session.getAttribute("gerenteLogado") != null) {
			Gerente gerente = (Gerente) session.getAttribute("gerenteLogado");
			ModelAndView mv = new ModelAndView("colaborador/profilegerente");
			mv.addObject("gerente", gerente);
			Iterable<Sugestao> sugestoes = sr.findAllInAnalise();
			mv.addObject("sugestoes", sugestoes);
			Iterable<Sugestao> todas = sr.findByColaborador(gerente);
			mv.addObject("sugestoesGerente", todas);
			return mv;
		} else {
			return loginGet();
		}
	}

	@GetMapping("/cadastrarColaborador")
	public ModelAndView form(HttpSession session) {
		ModelAndView mv = new ModelAndView("colaborador/formColaborador");
		Iterable<Cargo> cargos = crg.findAllexceptGerente();
		mv.addObject("cargo", cargos);
		Colaborador colaborador = new Colaborador();
		mv.addObject("gerente", colaborador);
		Cargo cargo = new Cargo();
		mv.addObject("cargoColaborador", cargo);
		if (session.getAttribute("AdmLogado") != null || session.getAttribute("gerenteLogado") != null) {
			return mv;
		} else {
			return loginGet();
		}
	}

	@RequestMapping(value = "/cadastrarColaborador", method = RequestMethod.POST)
	public String form(Colaborador colaborador, @RequestParam long cargoId) throws Exception {
		Cargo cargo = crg.findById(cargoId);
		colaborador.setCargo(cargo);
		cargo.getColaborador().add(colaborador);
		su.salvarColaborador(colaborador);
		crg.save(cargo);
		return "redirect:/cadastrarColaborador";
	}

	@GetMapping("/cadastrarGerente")
	public ModelAndView formGerente(HttpSession session) {
		ModelAndView mv = new ModelAndView("colaborador/formGerente");
		Iterable<Cargo> cargos = crg.findAllexceptAdm();
		mv.addObject("cargo", cargos);
		Gerente gerente = new Gerente();
		mv.addObject("gerente", gerente);
		Cargo cargo = new Cargo();
		mv.addObject("cargoGerente", cargo);
		if (session.getAttribute("AdmLogado") != null) {
			return mv;
		} else {
			return loginGet();
		}
	}

	@RequestMapping(value = "/cadastrarGerente", method = RequestMethod.POST)
	public String form(Gerente gerente, @RequestParam long cargoId) throws Exception {
		Cargo cargo = crg.findById(cargoId);
		gerente.setCargo(cargo);
		cargo.getColaborador().add(gerente);
		su.salvarGerente(gerente);
		crg.save(cargo);
		return "redirect:/cadastrarGerente";
	}

	@GetMapping("/cargos")
	public ModelAndView formCargos(HttpSession session) {
		if(session.getAttribute("AdmLogado") != null || session.getAttribute("gerenteLogado") != null) {
			ModelAndView mv = new ModelAndView("cargos");
			return mv;
		}
		else {
			return loginGet();
		}
	}

	@PostMapping("/cargos")
	public String formCargos(Cargo cargo) {
		su.salvarCargos(cargo);
		return "redirect:/cargos";

	}

	@GetMapping("/sugestoes")
	public ModelAndView listaSugestao(HttpSession session) {
		if (session.getAttribute("AdmLogado") != null) {
			ModelAndView mv = new ModelAndView("listSugestoes");
			Iterable<Sugestao> sugestoes = sr.findAll();
			mv.addObject("sugestoes", sugestoes);
			return mv;
		} else {
			return loginGet();
		}
	}

	@GetMapping("/colaboradores")
	public ModelAndView listaColaborador(HttpSession session) {
		if (session.getAttribute("AdmLogado") != null) {
			ModelAndView mv = new ModelAndView("colaborador/listaColaboradores");
			Iterable<Colaborador> colaboradores = cr.findAll();
			Iterable<Cargo> cargos = crg.findAll();
			mv.addObject("cargos", cargos);
			Cargo cargo = new Cargo();
			mv.addObject("cargoColaborador", cargo);

			mv.addObject("colaboradores", colaboradores);
			return mv;
		} else {
			return loginGet();
		}
	}

}
