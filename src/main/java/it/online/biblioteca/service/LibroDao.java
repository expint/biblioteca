package it.online.biblioteca.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Libro;
import it.online.biblioteca.utility.Costanti;

@Transactional
public class LibroDao implements Dao<Libro> {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void loadData() {	
		Query query = sessionFactory.getCurrentSession().createSQLQuery("LOAD DATA INFILE :filename INTO TABLE libri FIELDS TERMINATED BY ';' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES (titolo, autore, editore, anno, isbn, genere);");
		query.setString("filename", Costanti.RESOURCES_PATH+"elencoLibri.csv");
		query.executeUpdate();
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
