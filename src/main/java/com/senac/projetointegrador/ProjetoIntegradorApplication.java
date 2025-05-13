package com.senac.projetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

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

	@Controller
	public static class HomeController {

		@GetMapping("/")
		public String index() {
			return "Views/index";
		}
	}
}
