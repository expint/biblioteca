package it.online.biblioteca.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.ContattoDao;
import it.online.biblioteca.service.LibroDao;
import it.online.biblioteca.service.PrenotazioneDao;
import it.online.biblioteca.service.StatoUtenteDao;
import it.online.biblioteca.service.UtenteDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AdminDashboardController {
	@Autowired
	private CatalogoDao catalogoDao;
	@Autowired
	private LibroDao libroDao;
	@Autowired
	private PrenotazioneDao prenotazioneDao;
	@Autowired
	private StatoUtenteDao statoUtenteDao;
	@Autowired
	private UtenteDao utenteDao;
	@Autowired
	private ContattoDao contattoDao;
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	@RequestMapping("/admin")
	public String dashboard(@ModelAttribute("msg") Messaggio msg, Model model,  @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();

		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		
		//1. Libri in catalogo
		int quantiLibri = catalogoDao.readAllAvailable().size();
		
		//2. Utenti registrati
		int quantiUtenti = statoUtenteDao.readRegistrati().size();
		
		//3. Prenotazioni attive
		int quantePrenotazioniAttive = prenotazioneDao.readAttive().size();
		
		//4. Contatti ricevuti
		int quantiContatti = contattoDao.readAll().size();
		
		//5. Utenti da approvare
		int quantiDaApprovare = statoUtenteDao.readNuovi().size();
		
		//6. Prenotazioni totali
		int quantePrenotazioni = prenotazioneDao.readAll().size();
		
		model.addAttribute("libri", quantiLibri);
		model.addAttribute("prenotazioni", quantePrenotazioni);
		model.addAttribute("contatti", quantiContatti);
		model.addAttribute("utenti", quantiUtenti);
		model.addAttribute("prenAttive", quantePrenotazioniAttive);
		model.addAttribute("daApprovare", quantiDaApprovare);
		
		//tutte le query
		return "adminDashboard";
	}
	
}
