package it.online.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Catalogo;

@Transactional
public class CatalogoDao implements Dao<Catalogo>{
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Catalogo> readAll() {
		return sessionFactory.getCurrentSession().createQuery("from Catalogo").list();
	}

	@Override
	public Catalogo read(int id) {
		return (Catalogo) sessionFactory.getCurrentSession().get(Catalogo.class, id);
	}
	
	public Catalogo readFromIdLibro(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro.id = :libro group by c.libro.id");
		query.setInteger("libro", id);
		return (Catalogo) query.uniqueResult();
	}
	
	public List<Catalogo> readAvailableByIdLibro(int idLibro) {
		List<Catalogo> lista = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro = :id and c.stato <> 'non disponibile'");
			query.setInteger("id", idLibro);
			lista = query.list();
		} catch (HibernateException e) {
			
		}
		return lista;
	}
	
	public List<Catalogo> readAllAvailable() {
		List<Catalogo> lista = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.stato <> 'non disponibile' group by c.libro");
			lista = query.list();
		} catch (HibernateException e) {
			
		}
		return lista;
	}
	
	public Catalogo readFromISBN(String isbn) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro.isbn = :isbn group by c.libro.isbn");
		query.setParameter("isbn", isbn);
		return (Catalogo) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Catalogo> readAvailableByISBN(String isbn) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro.isbn = :isbn and c.stato = 'disponibile'");
		query.setParameter("isbn", isbn);
		List<Catalogo> lista = query.list();
		return lista;
	}
	
	public List<Catalogo> readAllByISBN(String isbn) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro.isbn = :isbn");
		query.setParameter("isbn", isbn);
		List<Catalogo> lista = query.list();
		return lista;
	}
	
	public List<Catalogo> readByAuthor(String author) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Catalogo c where c.libro.autore like '%:autore%'");
		query.setParameter("autore", author);
		List<Catalogo> lista = query.list();
		return lista;
	}
	
	public long quanteCopie(int libro, String stato) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT sum(CASE WHEN c.stato = :stato THEN 1 ELSE 0 END) FROM Catalogo c where c.libro.id = :libro group by c.libro.id");
		query.setParameter("stato", stato);
		query.setInteger("libro", libro);
		return (long) query.uniqueResult();
	}
	
	public long quanteCopie(String isbn, String stato) {
		Long quante = null;
		Query query;
		try {
			query = sessionFactory.getCurrentSession().createQuery("SELECT sum(CASE WHEN c.stato = :stato THEN 1 ELSE 0 END) FROM Catalogo c where c.libro.isbn = :isbn group by c.libro.isbn");
			query.setParameter("stato", stato);
			query.setParameter("isbn", isbn);
			quante = (long) query.uniqueResult();
		} catch (NullPointerException e) {
			quante = 0L;
		}
		return quante;
	}
	
	public long quanteCopieTotali(String isbn) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(c.libro.isbn) FROM Catalogo c where c.libro.isbn = :isbn");
		query.setParameter("isbn", isbn);
		return (long) query.uniqueResult();
	}
	
	public long quanteCopieTotali(int idLibro) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(c.libro) FROM Catalogo c where c.libro = :id");
		query.setInteger("id", idLibro);
		return (long) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> quantiLibri(int page, int risultati) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.libro.id FROM Catalogo c");
		int first = page>0? page:1;
		query.setFirstResult((first*risultati)-risultati);
		query.setMaxResults(risultati);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> libriPerAutore(int page, String autore, int ris) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.libro.id FROM Catalogo c where c.libro.autore like :autore");
		query.setParameter("autore", "%"+autore+"%");
		int first = page>0? page:1;
		query.setFirstResult((first*ris)-ris);
		query.setMaxResults(ris);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> libriPerTitolo(int page, String titolo, int ris) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.libro.id FROM Catalogo c where c.libro.titolo like :titolo");
		query.setParameter("titolo", "%"+titolo+"%");
		int first = page>0? page:1;
		query.setFirstResult((first*ris)-ris);
		query.setMaxResults(ris);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> libriPerGenere(int page, String genere, int ris) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.libro.id FROM Catalogo c where c.libro.genere like :genere");
		query.setParameter("genere", "%"+genere+"%");
		int first = page>0? page:1;
		query.setFirstResult((first*ris)-ris);
		query.setMaxResults(ris);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> libriPerISBN(int page, String isbn, int ris) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.libro.id FROM Catalogo c where c.libro.isbn like :isbn");
		query.setParameter("isbn", "%"+isbn+"%");
		int first = page>0? page:1;
		query.setFirstResult((first*ris)-ris);
		query.setMaxResults(ris);
		return query.list();
	}
	
	public int quantePagine(int p) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.libro.id)/:p) FROM Catalogo c ");
		query.setInteger("p", p);
		return (int) query.uniqueResult();
	}
	
	public int quantePagineAutore(String autore, int p) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.libro.id)/:p) FROM Catalogo c where c.libro.autore like :autore");
		query.setParameter("autore", "%"+autore+"%");
		query.setInteger("p", p);
		return (int) query.uniqueResult();
	}
	
	public int quantePagineTitolo(String titolo, int p) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.libro.id)/:p) FROM Catalogo c where c.libro.titolo like :titolo");
		query.setParameter("titolo", "%"+titolo+"%");
		query.setInteger("p", p);
		return (int) query.uniqueResult();
	}
	
	public int quantePagineGenere(String genere, int p) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.libro.id)/:p) FROM Catalogo c where c.libro.genere like :genere");
		query.setParameter("genere", "%"+genere+"%");
		query.setInteger("p", p);
		return (int) query.uniqueResult();
	}
	
	public int quantePagineISBN(String isbn, int p) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.libro.id)/:p) FROM Catalogo c where c.libro.isbn like :isbn");
		query.setParameter("isbn", "%"+isbn+"%");
		query.setInteger("p", p);
		return (int) query.uniqueResult();
	}

	@Override
	public Catalogo create(Catalogo catalogo) {
		int id = (Integer) sessionFactory.getCurrentSession().save(catalogo);
		return (Catalogo) sessionFactory.getCurrentSession().get(Catalogo.class, id);
	}

	@Override
	public void update(Catalogo catalogo) {
		sessionFactory.getCurrentSession().update(catalogo);
	}

	@Override
	public void delete(Catalogo catalogo) {
		sessionFactory.getCurrentSession().delete(catalogo);
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

}
