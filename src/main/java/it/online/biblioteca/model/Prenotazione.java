package it.online.biblioteca.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="prenotazioni")
public class Prenotazione {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="copia")
	private Catalogo catalogo;
	@ManyToOne
	@JoinColumn(name="utente")
	private Utente utente;
	private Date dataPrenotazione;
	private Date dataScadenza;
	private Date dataConsegna;
	private String stato;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Catalogo getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(Date dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public Date getDataConsegna() {
		return dataConsegna;
	}
	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Prenotazione(Catalogo catalogo, Utente utente, Date dataPrenotazione, Date dataScadenza, String stato) {
		super();
		this.catalogo = catalogo;
		this.utente = utente;
		this.dataPrenotazione = dataPrenotazione;
		this.dataScadenza = dataScadenza;
		this.stato = stato;
	}
	public Prenotazione() {
		super();
	}	
	
}
