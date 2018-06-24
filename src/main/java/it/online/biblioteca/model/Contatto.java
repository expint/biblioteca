package it.online.biblioteca.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contatti")
public class Contatto {
	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String oggetto;
	private String testo;
	private Date data;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Contatto(String email, String oggetto, String testo) {
		super();
		this.email = email;
		this.oggetto = oggetto;
		this.testo = testo;
	}
	public Contatto() {
		super();
	}
	public Contatto(String nome, String cognome, String email, String oggetto, String testo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.oggetto = oggetto;
		this.testo = testo;
	}
	
	public void annullaContatto() {
		this.id = -1;
		this.nome = null;
		this.cognome = null;
		this.email = null;
		this.oggetto = null;
		this.testo = null;
		this.data = null;
	}
	
}
