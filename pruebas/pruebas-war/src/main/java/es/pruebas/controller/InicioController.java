package es.pruebas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
	
	@GetMapping(value = { "/" })
	public String getInicio(Model model) {
		model.addAttribute("saludo", "Hola Mundo JOder");
		
		return "layout/index";}

}
