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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Libro;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.LibroDao;
import it.online.biblioteca.service.PrenotazioneDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AdminCatalogoController {
	@Autowired
	private CatalogoDao catalogoDao;
	@Autowired
	private PrenotazioneDao prenotazioneDao;
	@Autowired
	private LibroDao libroDao;

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
	
	public int gestionePagine(Model model, HttpServletRequest request) {
		boolean next = false;
		boolean prev = false;
		int pagineTotali = 0;
			
		pagineTotali = catalogoDao.quantePagine(Catalogo.ADMIN_PAGINATION);
		
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
	
	@RequestMapping("/admin/catalogo")
	public String visualizzaTutti(Model model, HttpServletRequest request, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente){
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		int pagina = 0;
		List<Integer> idLibri = null;
		
		pagina = this.gestionePagine(model, request);	
		idLibri = catalogoDao.quantiLibri(pagina, Catalogo.ADMIN_PAGINATION);
		
		Map<Catalogo, Integer> tuttilibri = new HashMap<>();
		for (Integer i: idLibri) {
			Catalogo catalogo = catalogoDao.readFromIdLibro(i);
			Integer qC = catalogoDao.readAvailableByIdLibro(i).size();
			tuttilibri.put(catalogo, qC);
		}
		
		model.addAttribute("mappa", tuttilibri);
		model.addAttribute("pagina", pagina);
		return "adminCatalogo";
	}
	
	@PostMapping("/admin/catalogo/aggiorna/{idLibro}")
	public String updateCatalogo(@PathVariable Integer idLibro, HttpServletRequest request,  @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		String titolo = request.getParameter("titolo");
		String autore = request.getParameter("autore");
		String editore = request.getParameter("editore");
		String isbn = request.getParameter("isbn");
		String genere = request.getParameter("genere");
		String classificazione = request.getParameter("classificazione");
		String anno = request.getParameter("anno");
		String copie = request.getParameter("copie");
		
		Libro libro = libroDao.read(idLibro);
		libro.setTitolo(titolo);
		libro.setAutore(autore);
		libro.setEditore(editore);
		
		try {
			if (anno != null && anno != "") {
				if (libro.getAnnoPubblicazione() != Integer.parseInt(anno)) {
					libro.setAnnoPubblicazione(Integer.parseInt(anno));
				}
			} else {
				libro.setAnnoPubblicazione(null);
			}
		} catch (NumberFormatException|NullPointerException e) {
			msg.sostituisciMessaggio(
					new Messaggio(Messaggio.ERROR, "Per l'anno di pubblicazione devi inserire un numero valido!"));
			return "redirect:/admin/catalogo";
		}	
		
		libro.setIsbn(isbn);
		libro.setGenere(genere);
		
		libroDao.update(libro);
		
		//gestione classificazione
		List<Catalogo> toUpdateClass = catalogoDao.readAllByISBN(libro.getIsbn());
		for (Catalogo catalogo : toUpdateClass) {
			catalogo.setClassificazione(classificazione);
			catalogoDao.update(catalogo);
		}
		
		//gestione copie
		int copieTotali = catalogoDao.readAvailableByIdLibro(idLibro).size();

			try {
				if (copieTotali<Integer.parseInt(copie)) {
					for (int i = 0; i < Integer.parseInt(copie)-copieTotali; i++) {
						catalogoDao.create(new Catalogo(libro, "disponibile", classificazione));						
					}
				}
				
				else if (Integer.parseInt(copie)<copieTotali) {
					if(prenotazioneDao.readCopieInPrestito(idLibro).size()>Integer.parseInt(copie)) {
						msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Ci sono copie di questo libro attualmente in prestito.<br>Non puoi eliminarle!"));
						return "redirect:/admin/catalogo";
					}
					else {
						List<Catalogo> disponibili = catalogoDao.readAvailableByISBN(libro.getIsbn());
						for (int i=0; i<disponibili.size()-(copieTotali-Integer.parseInt(copie));i++) {
							disponibili.get(i).setStato("non disponibile");
							catalogoDao.update(disponibili.get(i));
						}
					}
				}
			} catch (NumberFormatException e) {
				msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Se vuoi eliminare tutte le copie inserisci 0"));
				return "redirect:/admin/catalogo";
			}			
		

		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Catalogo aggiornato correttamente!"));
		
		return "redirect:/admin/catalogo";
	}
	
	@PostMapping("/admin/catalogo/aggiungi")
	public String aggiungiAlCatalogo(HttpServletRequest request,  @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		String titolo = request.getParameter("titolo");
		String autore = request.getParameter("autore");
		String editore = request.getParameter("editore");
		String isbn = request.getParameter("isbn");
		String genere = request.getParameter("genere");
		String classificazione = request.getParameter("classificazione");
		String anno = request.getParameter("anno");
		String copie = request.getParameter("copie");
		
		Libro libro = new Libro();
		libro.setTitolo(titolo);
		libro.setAutore(autore);
		libro.setEditore(editore);
		
		try {
				libro.setAnnoPubblicazione(Integer.parseInt(anno));
		} catch (NumberFormatException|NullPointerException e) {
			msg.sostituisciMessaggio(
					new Messaggio(Messaggio.ERROR, "Per l'anno di pubblicazione devi inserire un numero valido!"));
			return "redirect:/admin/catalogo";
		}	
		
		libro.setIsbn(isbn);
		libro.setGenere(genere);

		int copieToAdd = 0;
		
		try {
			copieToAdd = Integer.parseInt(copie);
		} catch (NumberFormatException e1) {
				msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Devi inserire quante copie vuoi inserire!"));
			return "redirect:/admin/catalogo";
		}

		Libro created = libroDao.create(libro);

		for (int i = 0; i < copieToAdd; i++) {
			catalogoDao.create(new Catalogo(created, "disponibile", classificazione));
		}
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Copie aggiunte al catalogo!"));
		
		return "redirect:/admin/catalogo";
	}
	

}
