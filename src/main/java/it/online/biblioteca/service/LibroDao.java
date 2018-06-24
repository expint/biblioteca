package it.online.biblioteca.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Libro;

@Transactional
public class LibroDao implements Dao<Libro> {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Libro> readAll(){
		return sessionFactory.getCurrentSession().createQuery("from Libro").list();
	}
	
	@Override
	public Libro read(int id) {
		return (Libro) sessionFactory.getCurrentSession().get(Libro.class, id);
	}
	
	@Override
	public Libro create(Libro libro) {
		int id = (Integer) sessionFactory.getCurrentSession().save(libro);
		return (Libro) sessionFactory.getCurrentSession().get(Libro.class, id);
	}
	
	@Override
	public void update(Libro libro) {
		sessionFactory.getCurrentSession().update(libro);
	}
	
	@Override
	public void delete(Libro libro) {
		sessionFactory.getCurrentSession().delete(libro);
	}
	
	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}
	

}
