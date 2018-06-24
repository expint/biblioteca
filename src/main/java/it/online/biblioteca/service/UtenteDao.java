package it.online.biblioteca.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Utente;

@Transactional
public class UtenteDao implements Dao<Utente> {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utente> readAll() {
		return sessionFactory.getCurrentSession().createQuery("from Utente").list();
	}
	
	public Utente couldLogin(Utente utente) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Utente where mail = :mail and password = :password");
		query.setParameter("mail", utente.getMail());
		query.setParameter("password", utente.getPassword());
		Utente selected = (Utente)query.uniqueResult();
		return selected;
	}
	
	public boolean verificaMail(Utente utente) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Utente where mail = :mail");
		query.setParameter("mail", utente.getMail());
		Utente selected = (Utente)query.uniqueResult();
		return selected!=null;
	}

	@Override
	public Utente read(int id) {
		return (Utente) sessionFactory.getCurrentSession().get(Utente.class, id);
	}
	
	public boolean contains(Utente utente) {
		return sessionFactory.getCurrentSession().contains(utente);
	}

	@Override
	public Utente create(Utente utente) {
		int id = (Integer) sessionFactory.getCurrentSession().save(utente);
		return (Utente) sessionFactory.getCurrentSession().get(Utente.class, id);
	}

	@Override
	public void update(Utente utente) {
		sessionFactory.getCurrentSession().update(utente);
	}

	@Override
	public void delete(Utente utente) {
		sessionFactory.getCurrentSession().delete(utente);
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

}
