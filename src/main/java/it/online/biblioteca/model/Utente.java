package it.online.biblioteca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="utenti")
public class Utente {
	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private String cognome;
	private String mail;
	private String password;
	@Type (type = "numeric_boolean")
	private boolean admin;
	
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}	
	public Utente() {
		super();
	}
	public Utente(String nome, String cognome, String mail, String password, boolean admin) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.password = password;
		this.admin = admin;
	}
	
	public Utente(String nome, String cognome, String mail, String password) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.password = password;
	}
	
	public Utente(String mail, String password) {
		super();
		this.mail = mail;
		this.password = password;
	}
	
	public void annullaUtente() {
		this.id = -1;
		this.nome = null;
		this.cognome = null;
		this.mail = null;
		this.password = null;
		this.admin = false;
	}
	
	
}