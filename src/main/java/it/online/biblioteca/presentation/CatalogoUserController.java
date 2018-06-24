package it.online.biblioteca.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.PrenotazioneDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class CatalogoUserController {
	@Autowired
	private CatalogoDao catalogoDao;
	@Autowired
	private PrenotazioneDao prenotazioneDao;
	
	public void setCatalogoDao(CatalogoDao catalogoDao) {
		this.catalogoDao = catalogoDao;
	}
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	public int gestionePagine(Model model, HttpServletRequest request, String search) {
		boolean next = false;
		boolean prev = false;
		int pagineTotali = 0;
		
		if (search.equals("")) {			
			pagineTotali = catalogoDao.quantePagine(8);
		}
		else if (search.equals("titolo")) {
			pagineTotali = catalogoDao.quantePagineTitolo(request.getParameter(search), 8);
		}
		else if (search.equals("autore")) {
			pagineTotali = catalogoDao.quantePagineAutore(request.getParameter(search), 8);
		}
		else if (search.equals("genere")) {
			pagineTotali = catalogoDao.quantePagineGenere(request.getParameter(search), 8);
		}
		else if (search.equals("isbn")) {
			pagineTotali = catalogoDao.quantePagineISBN(request.getParameter(search), 8);
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
	

	@RequestMapping("/catalogo")
	public String visualizza(Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg){
		msg.verificaMessaggio();
		
		String titolo=request.getParameter("titolo");
		String autore=request.getParameter("autore");
		String genere=request.getParameter("genere");
		String isbn=request.getParameter("isbn");
		String search = "";
		
		int pagina = 0;
		List<Integer> idLibri = null;
		
		if (titolo!=null && !titolo.equals("")) {
			pagina = this.gestionePagine(model, request, "titolo");
			idLibri = catalogoDao.libriPerTitolo(pagina, titolo, 8);
			search = "titolo="+titolo;
		}
		else if (autore!=null && !autore.equals("")) {
			pagina = this.gestionePagine(model, request, "autore");
			idLibri = catalogoDao.libriPerAutore(pagina, autore, 8);
			search = "autore="+autore;
		}
		else if (genere!=null && !genere.equals("")) {
			pagina = this.gestionePagine(model, request, "genere");
			idLibri = catalogoDao.libriPerGenere(pagina, genere, 8);
			search = "genere="+genere;
		}
		else if (isbn!=null && !isbn.equals("")) {
			pagina = this.gestionePagine(model, request, "isbn");
			idLibri = catalogoDao.libriPerISBN(pagina, isbn, 8);
			search = "isbn="+isbn;
		}
		else {
			pagina = this.gestionePagine(model, request, "");	
			idLibri = catalogoDao.quantiLibri(pagina, 8);
		}
		
		if (idLibri==null || idLibri.isEmpty()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Nessun libro trovato con i criteri cercati.<br>Riprova con altri parametri"));
			return "redirect:/catalogo";
		}
		
		Map<Catalogo, Integer> tuttilibri = new HashMap<>();
		for (Integer i: idLibri) {
			Catalogo catalogo = catalogoDao.readFromIdLibro(i);
			Integer qC = (int)catalogoDao.quanteCopie(i, "disponibile");
			tuttilibri.put(catalogo, qC);
		}
		model.addAttribute("mappa", tuttilibri);
		model.addAttribute("pagina", pagina);
		model.addAttribute("search", search);
		
		return "visualizzaCatalogo";
	} 
	
	@RequestMapping("/catalogo/{isbn}")
	public String visualizzaByIsbn(Model model, @PathVariable String isbn, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		Integer totali = (int) catalogoDao.quanteCopieTotali(isbn);

		if(totali==0) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Libro non disponibile.<br>Verificare che l'ISBN sia corretto"));
			return "redirect:/catalogo";
		}
		Catalogo catalogo = catalogoDao.readFromISBN(isbn);
		Integer qC = (int) catalogoDao.quanteCopie(isbn, "disponibile");
		
		if (utente.getId()>0) {
			Integer restituibile = prenotazioneDao.readIfRestituibile(utente.getId(), isbn);
			if (restituibile!=null && restituibile>0) {
				model.addAttribute("idCopiaDaRestituire", restituibile);
			}
		}

		model.addAttribute("catalogo", catalogo);
		model.addAttribute("disponibili", qC);
		model.addAttribute("totali", totali);
		return "libro";
	} 
	
}
