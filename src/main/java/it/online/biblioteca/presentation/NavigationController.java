package it.online.biblioteca.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {
	
	@RequestMapping("/dovesiamo")
	public String doveSiamo() {
		return "dovesiamo";
	}
	
	@RequestMapping("/faq")
	public String faq() {
		return "faq";
	}
	
	@RequestMapping("/termini")
	public String termini() {
		return "termini";
	}
	
	@RequestMapping("/privacy")
	public String privacy() {
		return "privacy";
	}
	
	@RequestMapping("/licenze")
	public String licenze() {
		return "licenze";
	}

}
