package com.senac.projetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.boot.CommandLineRunner;
import com.senac.projetointegrador.entities.Recepcionista;
import com.senac.projetointegrador.repositories.RecepcionistaRepository;

@SpringBootApplication
public class ProjetoIntegradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoIntegradorApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer forwardToIndex() {
		return new WebMvcConfigurer() {
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("Views/index");
			}
		};
	}

	@Bean
	public CommandLineRunner createDefaultRecepcionista(RecepcionistaRepository recepcionistaRepository) {
		return args -> {
			if (recepcionistaRepository != null && recepcionistaRepository.findByEmail("admin@clinac.com").isEmpty()) {
				Recepcionista admin = new Recepcionista(
					"Admin Recepção",
					"000.000.000-00",
					"admin@clinac.com",
					"(11)99999-9999",
					"Feminino",
					"São Paulo",
					"SP",
					"admin"
				);
				recepcionistaRepository.save(admin);
			}
		};
	}

	@Controller
	public static class HomeController {

		@GetMapping("/")
		public String index() {
			return "Views/index";
		}
	}
}
