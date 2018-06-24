package it.online.biblioteca.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.Contatto;

@Transactional
public class ContattoDao implements Dao<Contatto>{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contatto> readAll() {
		return sessionFactory.getCurrentSession().createQuery("from Contatto").list();
	}

	@Override
	public Contatto read(int id) {
		return (Contatto) sessionFactory.getCurrentSession().get(Contatto.class, id);
	}

	@Override
	public Contatto create(Contatto contatto) {
		int id = (Integer) sessionFactory.getCurrentSession().save(contatto);
		return (Contatto) sessionFactory.getCurrentSession().get(Contatto.class, id);
	}
	
	public int quantePagine(int pag) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT ceil(count(distinct c.id)/:pag) FROM Contatto c order by c.id desc");
		query.setInteger("pag", pag);
		return (int) query.uniqueResult();
	}
	
	public List<Integer> quantiContatti(int page, int risultati) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT distinct c.id FROM Contatto c order by c.id desc");
		int first = page>0? page:1;
		query.setFirstResult((first*risultati)-risultati);
		query.setMaxResults(risultati);
		return query.list();
	}

	@Override
	public void update(Contatto contatto) {
		sessionFactory.getCurrentSession().update(contatto);
	}

	@Override
	public void delete(Contatto contatto) {
		sessionFactory.getCurrentSession().delete(contatto);
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

}
