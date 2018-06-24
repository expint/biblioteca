package it.online.biblioteca.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Contatto;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.ContattoDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AdminContattiController {
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
	
	public int gestionePagine(Model model, HttpServletRequest request) {
		boolean next = false;
		boolean prev = false;
		int pagineTotali = 0;
			
		pagineTotali = contattoDao.quantePagine(Catalogo.ADMIN_PAGINATION);			

		
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
	
	@RequestMapping("/admin/contatti")
	public String visualizzaContatti(@ModelAttribute("msg") Messaggio msg, Model model,  @ModelAttribute("utente") Utente utente, HttpServletRequest request) {
		msg.verificaMessaggio();

		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<Contatto> contatti = new ArrayList<>();
		
		int pagina = 0;
		List<Integer> idContact = null;
		
		pagina = this.gestionePagine(model, request);	
		idContact = contattoDao.quantiContatti(pagina, Catalogo.ADMIN_PAGINATION);
		
		for (Integer i: idContact) {
			Contatto contatto = contattoDao.read(i);
			contatti.add(contatto);
		}
		
		
		model.addAttribute("contatti", contatti);
		model.addAttribute("pagina", pagina);

		return "adminContatti";
	}
	
}
