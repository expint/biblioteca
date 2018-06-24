package it.online.biblioteca.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Permette di definire lo stato corrente dell'utente
 * (attivo, sospeso, nuovo) con le relative date.
 * Per capire lo stato dell'utente basterà prendere il primo valore in ordine descrescente
 * rispetto all'operazione dell'utente
 */
@Entity
@Table(name="statoutenti")
public class StatoUtente {	
	@Id
	@GeneratedValue
	private int operazione;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="utente")
	private Utente utente;
	private Date dataInizio;
	private Date dataFine;
	private String stato;
	public int getOperazione() {
		return operazione;
	}
	public void setOperazione(int operazione) {
		this.operazione = operazione;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public StatoUtente(Utente utente, Date dataInizio, Date dataFine, String stato) {
		super();
		this.utente = utente;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.stato = stato;
	}
	public StatoUtente() {
		super();
	}
	
}
