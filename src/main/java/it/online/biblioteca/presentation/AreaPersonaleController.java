package it.online.biblioteca.presentation;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Messaggio;
import it.online.biblioteca.model.Prenotazione;
import it.online.biblioteca.model.StatoUtente;
import it.online.biblioteca.model.Utente;
import it.online.biblioteca.service.CatalogoDao;
import it.online.biblioteca.service.PrenotazioneDao;
import it.online.biblioteca.service.StatoUtenteDao;
import it.online.biblioteca.service.UtenteDao;

@Controller
@SessionAttributes(value = {"utente", "msg"})
public class AreaPersonaleController {
	@Autowired
	private StatoUtenteDao statoDao;
	@Autowired
	private UtenteDao utenteDao;
	@Autowired
	private CatalogoDao catalogoDao;
	@Autowired
	private PrenotazioneDao prenotazioneDao;
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("msg") Messaggio msg) {
		msg.verificaMessaggio();
		return "login";
	}
	
	
	@RequestMapping("/logout")
	public String logout(@ModelAttribute("utente") Utente utente, @ModelAttribute("msg") Messaggio msg) {
		utente.annullaUtente();
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS,"Logout effettuato correttamente. Torna presto a trovarci!"));
		return "redirect:/";
	}
	
	@ModelAttribute("utente")
	public Utente getUtente() {
		return new Utente();
	}
	
	@ModelAttribute("msg")
	public Messaggio getMessaggio() {
		return new Messaggio();
	}
	
	@PostMapping("/dologin")
	public String doLogin(Model model, @ModelAttribute("utente") Utente utente, @ModelAttribute("msg") Messaggio msg) {
		Messaggio local = msg;
		msg.annullaMessaggio();
		
		Utente isInto = utenteDao.couldLogin(utente);
		if (isInto!=null) {
			System.out.println("Utente presente nell'archivio");	
			utente.setNome(isInto.getNome());
			String isAttivo = null;
			List<StatoUtente> statoCorrente = statoDao.statoUtenteCorrente(isInto.getId());
			if (statoCorrente!=null && !statoCorrente.isEmpty()) {
				isAttivo = statoCorrente.get(0).getStato();
				if (isAttivo.equals("attivo")) {
					utente.setId(isInto.getId());
					utente.setCognome(isInto.getCognome());
					utente.setAdmin(isInto.isAdmin());
					utente.setMail(isInto.getMail());
					System.out.println("Utente "+isInto.getMail()+" attivo");
				}
				else if(isAttivo.equals("sospeso")) {
					System.out.println("Utente sospeso");
					utente.annullaUtente();
					local = new Messaggio(Messaggio.ERROR, "Sei stato sospeso dal servizio prenotazioni. <br>Contattaci per ulteriori informazioni");
					msg.sostituisciMessaggio(local);
					return "redirect:/login";
				}
				else {			
					System.out.println("Utente non attivo");
					utente.annullaUtente();
					local = new Messaggio(Messaggio.WARNING, "Registrazione non convalidata dall'amministratore. Riprova entro 24h");
					msg.sostituisciMessaggio(local);
					return "redirect:/login";
				}
			}
			else {
				utente.setMail(null);
				local = new Messaggio(Messaggio.ERROR, "Utente non registrato correttamente.");
				msg.sostituisciMessaggio(local);
				System.out.println("Utente presente ma non registrato. Procedere con una registrazione?");
				return "redirect:/login";
			}
		}
		else {			
			System.out.println("Utente non presente nell'archivio");	
			utente.annullaUtente();
			local = new Messaggio(Messaggio.ERROR, "Email o password non corrette. Riprova");
			msg.sostituisciMessaggio(local);
			return "redirect:/login";
		}
		return "redirect:/areapersonale";
	}
	
	/**
	 * Verifica se l'utente è già presente in archivio 
	 * o se fare una nuova registrazione. 
	 * In entrambi i casi ritorna al login perchè deve aspettare la convalida dell'admin
	 * 
	 * @param model
	 * @param utente
	 * @param msg
	 * @return
	 */
	@PostMapping("/registrati")
	public String registrati(Model model, @ModelAttribute("utente") Utente utente, @ModelAttribute("msg") Messaggio msg) {
		Messaggio local = msg;
		msg.annullaMessaggio();
		boolean isAlreadyRegistered = utenteDao.verificaMail(utente);
		if (isAlreadyRegistered) {
			System.out.println("Mail già presente in archivio");
			local = new Messaggio(Messaggio.ERROR, "Utente già registrato. Se hai dimenticato la password contattaci");
			msg.sostituisciMessaggio(local);
		}
		else {
			Utente creato = utenteDao.create(utente);
			statoDao.create(new StatoUtente(creato, new Date(), null, "nuovo"));
			System.out.println("Utente creato con successo");
			local = new Messaggio(Messaggio.SUCCESS, "Registrazione effettuata correttamente");
			msg.sostituisciMessaggio(local);
		}
		utente.annullaUtente();
		return "redirect:/login";
	}
	
	@PostMapping("/prenota/{isbn}")
	public String prenota(Model model, @ModelAttribute("utente") Utente utente, @PathVariable String isbn, @ModelAttribute("msg") Messaggio msg) {
		Messaggio local = msg;
		msg.annullaMessaggio();
		Utente uPrenotazione = utenteDao.read(utente.getId());
		List<Catalogo> disponibili = catalogoDao.readAvailableByISBN(isbn);
		Catalogo disponibile = null;
		if (disponibili != null && !disponibili.isEmpty()) {
			disponibile = disponibili.get(0);
			if (prenotazioneDao.quanteAttive(uPrenotazione.getId()) < 3L) {

				Date ora = new Date();
				long mesesuccessivo = ora.getTime() + 2592000000L;
				prenotazioneDao.create(new Prenotazione(disponibile, uPrenotazione, ora, new Date(mesesuccessivo), "in corso"));
				disponibile.setStato("in prestito");
				catalogoDao.update(disponibile);
				System.out.println("prenotazione eseguita con successo");
				local = new Messaggio(Messaggio.SUCCESS, "Prenotazione effettuata correttamente");
				msg.sostituisciMessaggio(local);
			} 
			else {
				local = new Messaggio(Messaggio.ERROR, "Hai gi&agrave; 3 prenotazioni attive. Devi riconsegnare i libri che hai prima di prenotarne altri.");
				msg.sostituisciMessaggio(local);
				System.out.println("prenotazione fallita: hai già 3 prenotazioni attive");
			}
		}
		else {
			local = new Messaggio(Messaggio.ERROR, "Libro non disponibile per la prenotazione. Riprova più tardi");
			msg.sostituisciMessaggio(local);
			System.out.println("Libro non disponibile per la prenotazione");
		}
		return "redirect:/catalogo";
	}
	
	@PostMapping("/restituisci/{idCopia}")
	public String restituisci(Model model, @ModelAttribute("utente") Utente utente, @PathVariable int idCopia, @ModelAttribute("msg") Messaggio msg) {
		msg.annullaMessaggio();
		Prenotazione prenotazione = prenotazioneDao.read(utente.getId(), idCopia);
		prenotazione.setDataConsegna(new Date());
		prenotazione.setStato("consegnata");
		
		Catalogo restituito = catalogoDao.read(prenotazione.getCatalogo().getId());
		restituito.setStato("disponibile");
		
		catalogoDao.update(restituito);
		prenotazioneDao.update(prenotazione);
		
		msg.sostituisciMessaggio(new Messaggio(Messaggio.SUCCESS, "Copia libro restituita.<br>Ora puoi prenotare un altro libro.<br>Grazie!"));
		return "redirect:/areapersonale";
	}
	
	@GetMapping("/areapersonale")
	public String areaPersonale(@ModelAttribute("utente") Utente utente, @ModelAttribute("msg") Messaggio msg, Model model) {
		msg.verificaMessaggio();
		if(utente.getMail()!=null && utente.getMail()!="") {
			List<Prenotazione> attive = prenotazioneDao.getPrenotazioniAttiveUtente(utente.getId());
			Map<Integer, List<Prenotazione>> mappa = prenotazioniPerAnno(utente.getId());
			Map<Integer, List<Prenotazione>> ordinata = new TreeMap<>(mappa);
			model.addAttribute("attive", attive);
			model.addAttribute("mappa", ordinata);
			return "areaPersonale";
		}
		return "redirect:/login";
	}
	
	public Map<Integer, List<Prenotazione>> prenotazioniPerAnno(int id){
		return prenotazioneDao.getAllPrenotazioniUtente(id);
	}
}
