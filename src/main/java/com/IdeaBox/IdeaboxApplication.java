package com.IdeaBox;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.IdeaBox.models.cargos.Cargo;
import com.IdeaBox.models.usuarios.Administrador;
import com.IdeaBox.models.usuarios.Gerente;
import com.IdeaBox.repository.AdminRepository;
import com.IdeaBox.repository.CargoRepository;
import com.IdeaBox.repository.GerenteRepository;

@SpringBootApplication
@ComponentScan
public class IdeaboxApplication implements CommandLineRunner {

	@Autowired
	CargoRepository crg;
	
	@Autowired
	AdminRepository ar;
	
	@Autowired
	GerenteRepository gr;
	
	public static void main(String[] args) {
		SpringApplication.run(IdeaboxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Cargo cargo1 = new Cargo(1,"Administrador");
		Cargo cargo2 = new Cargo(2,"Gerente");
		
		Administrador adm = new Administrador(1, "Administrador", "13254743040", cargo1, "admin", "1234567", "admin@senac.com.br");
		cargo1.getColaborador().add(adm);
		
		this.crg.saveAll(Arrays.asList(cargo1, cargo2));
		this.ar.saveAll(Arrays.asList(adm));
	}

}
