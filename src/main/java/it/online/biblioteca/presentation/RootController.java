package it.online.biblioteca.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;

@Controller
@SessionAttributes(value = {"utente", "msg"})
@RequestMapping("/")
public class RootController {

	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String homee(@ModelAttribute("msg") Messaggio msg) {
		msg.verificaMessaggio();
		return "home";
	}

}
