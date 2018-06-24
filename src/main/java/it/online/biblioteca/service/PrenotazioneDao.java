package it.online.biblioteca.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Catalogo;
import it.online.biblioteca.model.Prenotazione;

@Transactional
public class PrenotazioneDao implements Dao<Prenotazione>{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Prenotazione> readAll() {
		return sessionFactory.getCurrentSession().createQuery("from Prenotazione").list();
	}
	
	public long quanteAttive(int idUtente) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from Prenotazione p where p.utente = :utente and p.stato = 'in corso'");
		query.setInteger("utente", idUtente);
		return (long) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Prenotazione> getPrenotazioniAttiveUtente(int idUtente){
		Query query = sessionFactory.getCurrentSession().createQuery("from Prenotazione p where p.utente = :utente and p.stato = 'in corso'");
		query.setInteger("utente", idUtente);
		return query.list();
	}
	
	public List<Prenotazione> readAttive(){
		Query query = sessionFactory.getCurrentSession().createQuery("from Prenotazione p where p.stato = 'in corso'");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, List<Prenotazione>> getAllPrenotazioniUtente(int idUtente){
		Map<Integer, List<Prenotazione>> ris = new HashMap<>();
		Query query = sessionFactory.getCurrentSession().createQuery("select distinct EXTRACT(YEAR FROM p.dataPrenotazione) from Prenotazione p where p.utente = :utente order by EXTRACT(YEAR FROM p.dataPrenotazione) desc");
		List<Integer> anni = query.setInteger("utente", idUtente).list();
		for (Integer anno : anni) {
			Query queryLista = sessionFactory.getCurrentSession().createQuery("from Prenotazione p where p.utente = :utente and EXTRACT(YEAR FROM p.dataPrenotazione) like :anno");
			queryLista.setInteger("utente", idUtente);
			queryLista.setParameter("anno", anno);
			List<Prenotazione> prenotazioniperanno = queryLista.list();
			ris.put(anno, prenotazioniperanno);
		}
		
		return ris;
	}

	@Override
	public Prenotazione read(int id) {
		return (Prenotazione) sessionFactory.getCurrentSession().get(Prenotazione.class, id);
	}
	
	public Prenotazione read(int idUtente, int idCopia) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Prenotazione p where p.utente = :utente and p.catalogo.id = :copia and p.stato = 'in corso'");
		query.setInteger("utente", idUtente);
		query.setParameter("copia", idCopia);
		return (Prenotazione) query.uniqueResult();
	}
	
	public Integer readIfRestituibile(int idUtente, String isbn) {
		Query query = sessionFactory.getCurrentSession().createQuery("select p.id from Prenotazione p where p.utente = :utente and p.catalogo.libro.isbn = :isbn and p.stato = 'in corso' order by p.id");
		query.setInteger("utente", idUtente);
		query.setParameter("isbn", isbn);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return (Integer) query.uniqueResult();
	}
	
	public List<Catalogo> readCopieInPrestito(int idLibro) {
		Query query = sessionFactory.getCurrentSession().createQuery("select p.catalogo from Prenotazione p where p.catalogo.libro = :id and p.stato = 'in corso'");
		query.setInteger("id", idLibro);
		return query.list();
	}
	
	public int quantePagine(int pag) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct p.id)/:pag) FROM Prenotazione p order by p.id desc");
		query.setInteger("pag", pag);
		return (int) query.uniqueResult();
	}
	
	public List<Integer> quantePrenotazioni(int page, int risultati) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct p.id FROM Prenotazione p order by p.id desc");
		int first = page>0? page:1;
		query.setFirstResult((first*risultati)-risultati);
		query.setMaxResults(risultati);
		return query.list();
	}
	
	public int quantePaginePerUtente(int pag, int idUtente) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct p.id)/:pag) FROM Prenotazione p where p.utente.id = :idUtente order by p.id desc");
		query.setInteger("pag", pag);
		query.setInteger("idUtente", idUtente);
		return (int) query.uniqueResult();
	}
	
	public List<Integer> quantePrenotazioniPerUtente(int page, int risultati, int idUtente) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct p.id FROM Prenotazione p where p.utente.id = :idUtente order by p.id desc");
		query.setInteger("idUtente", idUtente);
		int first = page>0? page:1;
		query.setFirstResult((first*risultati)-risultati);
		query.setMaxResults(risultati);
		return query.list();
	}
	
	public List<Integer> quantePrenotazioniAttive(int page, int risultati) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct p.id FROM Prenotazione p where p.stato='in corso' order by p.id desc");
		int first = page>0? page:1;
		query.setFirstResult((first*risultati)-risultati);
		query.setMaxResults(risultati);
		return query.list();
	}

	@Override
	public Prenotazione create(Prenotazione prenotazione) {
		int id = (Integer) sessionFactory.getCurrentSession().save(prenotazione);
		return (Prenotazione) sessionFactory.getCurrentSession().get(Prenotazione.class, id);
	}

	@Override
	public void update(Prenotazione prenotazione) {
		sessionFactory.getCurrentSession().update(prenotazione);
	}

	@Override
	public void delete(Prenotazione prenotazione) {
		sessionFactory.getCurrentSession().delete(prenotazione);
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

}
