package it.online.biblioteca.presentation;

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

import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.StatoUtente;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.StatoUtenteDao;
import it.online.biblioteca.service.UtenteDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AdminUtentiController {
	@Autowired
	private StatoUtenteDao statoUtenteDao;
	@Autowired
	private UtenteDao utenteDao;
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	@RequestMapping("/admin/utenti/approva")
	public String daApprovare(@ModelAttribute("msg") Messaggio msg, Model model,  @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<StatoUtente> nuovi = statoUtenteDao.readNuovi();
		model.addAttribute("utenti", nuovi);
		return "utentiDaApprovare";
	}
	
	@RequestMapping("/admin/utenti")
	public String vediUtenti(@ModelAttribute("msg") Messaggio msg, Model model,  @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		List<StatoUtente> registrati = statoUtenteDao.readRegistrati();
		model.addAttribute("utenti", registrati);
		return "tuttiGliUtenti";
	}
	
	@RequestMapping("/admin/utenti/approva/{idUtente}")
	public String approva(@PathVariable String idUtente, @ModelAttribute("msg") Messaggio msg, Model model, HttpServletRequest request,  @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		String bAdmin = request.getParameter("approvaBtn");
		StatoUtente statoUtente = statoUtenteDao.statoUtenteCorrente(Integer.parseInt(idUtente)).get(0);
		
		if (bAdmin.equals("1")) {
			statoUtente.getUtente().setAdmin(true);
		}
		
		statoUtente.setDataFine(new Date());
		statoUtenteDao.update(statoUtente);
		statoUtenteDao.create(new StatoUtente(statoUtente.getUtente(), new Date(), null, "attivo"));
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Registrazione utente approvata con successo!"));
		
		return "redirect:/admin/utenti/approva";
	}
	
	@RequestMapping("/admin/utenti/sospendi/{idUtente}")
	public String sospendi(@PathVariable String idUtente, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		StatoUtente statoUtente = statoUtenteDao.statoUtenteCorrente(Integer.parseInt(idUtente)).get(0);
		statoUtente.setDataFine(new Date());
		statoUtenteDao.update(statoUtente);
		statoUtenteDao.create(new StatoUtente(statoUtente.getUtente(), new Date(), null, "sospeso"));
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Utente sospeso!"));
		
		return "redirect:/admin/utenti";
	}
	
	@RequestMapping("/admin/utenti/attiva/{idUtente}")
	public String attiva(@PathVariable String idUtente, @ModelAttribute("msg") Messaggio msg, @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		StatoUtente statoUtente = statoUtenteDao.statoUtenteCorrente(Integer.parseInt(idUtente)).get(0);
		statoUtente.setDataFine(new Date());
		statoUtenteDao.update(statoUtente);
		statoUtenteDao.create(new StatoUtente(statoUtente.getUtente(), new Date(), null, "attivo"));
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Utente attivato!"));
		
		return "redirect:/admin/utenti";
	}
	
	@PostMapping("/admin/utenti/rimuovi/{idUtente}")
	public String rimuovi(@PathVariable String idUtente, @ModelAttribute("msg") Messaggio msg, Model model, @ModelAttribute("utente") Utente utente) {
		msg.verificaMessaggio();
		
		if(!utente.isAdmin()) {
			msg.sostituisciMessaggio(new Messaggio(Messaggio.ERROR, "Non hai i permessi per vedere questa pagina!"));
			return "redirect:/";
		}
		
		StatoUtente statoUtente = statoUtenteDao.statoUtenteCorrente(Integer.parseInt(idUtente)).get(0);
		System.out.println(statoUtente.getOperazione());
		
		statoUtenteDao.deleteByIdUtente(Integer.parseInt(idUtente));
		
		Utente todelete = utenteDao.read(Integer.parseInt(idUtente));
		utenteDao.delete(todelete);
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Utente rimosso con successo!"));
		
		return "redirect:/admin/utenti/approva";
	}

}
