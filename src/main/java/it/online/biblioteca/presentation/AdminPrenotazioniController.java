package it.online.biblioteca.presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Prenotazione;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.PrenotazioneDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AdminPrenotazioniController {
	@Autowired
	private PrenotazioneDao prenotazioneDao;
	@Autowired
	private CatalogoDao catalogoDao;
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	public int gestionePagine(Model model, HttpServletRequest request, int idUtente) {
		boolean next = false;
		boolean prev = false;
		int pagineTotali = 0;
			
		if (idUtente==0) {
			pagineTotali = prenotazioneDao.quantePagine(Catalogo.ADMIN_PAGINATION);			
		}
		else {
			pagineTotali = prenotazioneDao.quantePaginePerUtente(Catalogo.ADMIN_PAGINATION, idUtente);
		}
		
		Integer pagina = null;
		
		try {
			pagina = Integer.parseInt(request.getParameter("p"));
		} catch (NumberFormatException e) {
			pagina = 1;
		}		
			
		if (pagina > pagineTotali) {
			pagina = pagineTotali;
		}

		if (pagina == 1 && pagineTotali != 1) {
			next = true;
		} 
		else if (pagina == pagineTotali && pagineTotali != 1) {
			prev = true;
		} 
		else if (pagina == pagineTotali && pagineTotali == 1) {
			next = false;
			prev = false;
		} 
		else {
			prev = true;
			next = true;
		}
		
		if(pagineTotali!=0) {
			model.addAttribute("prev", prev);
			model.addAttribute("next", next);
		}
		return pagina;
	}
	
	@RequestMapping("/admin/prenotazioni/attive")
	public String visualizzaAttive(Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<Prenotazione> prenotazioniAttive = new ArrayList<>();
		
		int pagina = 0;
		List<Integer> idPrenot = null;
		
		pagina = this.gestionePagine(model, request, 0);	
		idPrenot = prenotazioneDao.quantePrenotazioniAttive(pagina, Catalogo.ADMIN_PAGINATION);
		
		for (Integer i: idPrenot) {
			Prenotazione prenotazione = prenotazioneDao.read(i);
			prenotazioniAttive.add(prenotazione);
		}
		
		
		model.addAttribute("prenotazioni", prenotazioniAttive);
		model.addAttribute("pagina", pagina);
		return "prenotazioniAttive";
	}
	
	@RequestMapping("/admin/prenotazioni")
	public String visualizzaTutte(Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<Prenotazione> prenotazioni = new ArrayList<>();
		
		int pagina = 0;
		List<Integer> idPrenot = null;
		
		pagina = this.gestionePagine(model, request, 0);	
		idPrenot = prenotazioneDao.quantePrenotazioni(pagina, Catalogo.ADMIN_PAGINATION);
		
		for (Integer i: idPrenot) {
			Prenotazione prenotazione = prenotazioneDao.read(i);
			prenotazioni.add(prenotazione);
		}
		
		
		model.addAttribute("prenotazioni", prenotazioni);
		model.addAttribute("pagina", pagina);
		return "prenotazioni";
	}
	
	@RequestMapping("/admin/prenotazioni/{idUtente}")
	public String visualizzaTutte(@PathVariable int idUtente, Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<Prenotazione> prenotazioni = new ArrayList<>();
		
		int pagina = 0;
		List<Integer> idPrenot = null;
		
		pagina = this.gestionePagine(model, request, idUtente);	
		idPrenot = prenotazioneDao.quantePrenotazioniPerUtente(pagina, Catalogo.ADMIN_PAGINATION, idUtente);
		
		for (Integer i: idPrenot) {
			Prenotazione prenotazione = prenotazioneDao.read(i);
			prenotazioni.add(prenotazione);
		}
		
		model.addAttribute("titolo", "Prenotazioni di");
		model.addAttribute("prenotazioni", prenotazioni);
		model.addAttribute("pagina", pagina);
		return "prenotazioni";
	}
	
	@PostMapping("/admin/prenotazioni/chiudi/{id}")
	public String chiudi(@PathVariable int id, Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		Prenotazione prenotazione = prenotazioneDao.read(id);
		prenotazione.setDataConsegna(new Date());
		prenotazione.setStato("chiusa");
		
		Catalogo restituito = catalogoDao.read(prenotazione.getCatalogo().getId());
		restituito.setStato("disponibile");
		
		catalogoDao.update(restituito);
		prenotazioneDao.update(prenotazione);
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Prenotazione chiusa correttamente"));
		
		return "redirect:/admin/prenotazioni";
	}

}
