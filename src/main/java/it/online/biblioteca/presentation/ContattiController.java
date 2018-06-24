package it.online.biblioteca.presentation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Contatto;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.service.ContattoDao;

@Controller
@SessionAttributes(value = {"msg", "contatto"})
public class ContattiController {
	@Autowired
	private ContattoDao contattoDao;

	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@ModelAttribute("contatto")
	public Contatto getContatto() {
		return new Contatto();
	}
	
	@RequestMapping("/contatti")
	public String contatti(@ModelAttribute("msg") Messaggio msg) {
		msg.verificaMessaggio();
		return "contatti";
	}
	
	@PostMapping("/aggiungiContatto")
	public String registrati(@ModelAttribute("msg") Messaggio msg, @ModelAttribute("contatto") Contatto contatto) {
		Messaggio local = msg;
		msg.annullaMessaggio();
		
//		if (utente.getNome()!=null && utente.getNome()!="") {
//			contatto.setNome(utente.getNome());
//			contatto.setCognome(utente.getCognome());
//			contatto.setEmail(utente.getMail());
//		}
		
		contatto.setData(new Date());
		
		contattoDao.create(contatto);
		local = new Messaggio(Messaggio.SUCCESS, "Messaggio inviato correttamente.<br>Riceverai presto notizie.<br>Torna a trovarci!");
		msg.sostituisciMessaggio(local);
		contatto.annullaContatto();
		
		return "redirect:/contatti";
	}
	
}
