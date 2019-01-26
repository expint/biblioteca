package it.online.biblioteca.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.LibroDao;
import it.online.biblioteca.service.PrenotazioneDao;
import it.online.biblioteca.service.StatoUtenteDao;
import it.online.biblioteca.service.UtenteDao;
import it.online.biblioteca.utility.Costanti;

@Controller
@SessionAttributes(value = {"utente", "msg"})
@RequestMapping("/")
public class RootController {
	@Autowired
	private LibroDao libroDao;
	@Autowired
	private CatalogoDao catalogoDao;
	@Autowired
	private UtenteDao utenteDao;
	@Autowired
	private StatoUtenteDao statoUtenteDao;
	@Autowired
	private PrenotazioneDao prenotazioneDao;

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
		this.caricaDati(Costanti.TOLOAD);
		msg.verificaMessaggio();
		return "home";
	}
	
	private void caricaDati(boolean load) {
		if(load) {
			if(libroDao.readAll().isEmpty()) {
				libroDao.loadData();
			}
			if(catalogoDao.readAll().isEmpty()) {
				catalogoDao.loadData();
			}
			if(utenteDao.readAll().isEmpty()) {
				utenteDao.loadData();
			}
			if(statoUtenteDao.readAll().isEmpty()) {
				statoUtenteDao.loadData();
			}
			if(prenotazioneDao.readAll().isEmpty()) {
				prenotazioneDao.loadData();
			}
		}
	}

}
