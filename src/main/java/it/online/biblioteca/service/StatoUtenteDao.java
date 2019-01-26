package it.online.biblioteca.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import it.online.biblioteca.model.StatoUtente;
import it.online.biblioteca.utility.Costanti;

@Transactional
public class StatoUtenteDao implements Dao<StatoUtente> {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void loadData() {	
		Query query = sessionFactory.getCurrentSession().createSQLQuery("LOAD DATA INFILE :filename INTO TABLE statoutenti FIELDS TERMINATED BY ';' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES (utente, stato, @dataInizio, @dataFine) "
				+ "SET dataInizio = CASE WHEN @dataInizio = \'null\' THEN null ELSE now() + interval @dataInizio minute END, " 
				+ "dataFine = CASE WHEN @dataFine = 'null' THEN null ELSE now() + interval @dataFine minute END;");
		query.setString("filename", Costanti.RESOURCES_PATH+"statoUtenti.csv");
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StatoUtente> readAll() {
		return sessionFactory.getCurrentSession().createQuery("from StatoUtente").list();
	}
	
	public List<StatoUtente> readNuovi() {
		return sessionFactory.getCurrentSession().createQuery("from StatoUtente s where s.stato = 'nuovo' and s.dataFine=null").list();
	}
	
	public List<StatoUtente> readRegistrati() {
		return sessionFactory.getCurrentSession().createQuery("from StatoUtente s where s.stato <> 'nuovo' and s.dataFine=null").list();
	}

	@Override
	public StatoUtente read(int id) {
		return (StatoUtente) sessionFactory.getCurrentSession().get(StatoUtente.class, id);
	}
	
	public boolean contains(StatoUtente statoUtente) {
		return sessionFactory.getCurrentSession().contains(statoUtente);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatoUtente> statoUtenteCorrente(int idUtente) {
		Query query = sessionFactory.getCurrentSession().createQuery("from StatoUtente S where S.utente = :utente order by S.dataInizio desc");
		query.setInteger("utente", idUtente);
		List<StatoUtente> lista = query.list();
		return lista;
	}

	@Override
	public StatoUtente create(StatoUtente statoUtente) {
		int id = (Integer) sessionFactory.getCurrentSession().save(statoUtente);
		return (StatoUtente) sessionFactory.getCurrentSession().get(StatoUtente.class, id);
	}

	@Override
	public void update(StatoUtente statoUtente) {
		sessionFactory.getCurrentSession().update(statoUtente);
	}


	public int deleteByIdUtente(int idUtente) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from StatoUtente S where S.utente = :id");
		query.setInteger("id", idUtente);
		return query.executeUpdate();
	}

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void delete(StatoUtente t) {
		// TODO Auto-generated method stub
		
	}

}
