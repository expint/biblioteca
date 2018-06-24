package it.online.biblioteca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="catalogo")
public class Catalogo {
	public static final int PAGINATION = 8;	
	public static final int ADMIN_PAGINATION = 15;	
	
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="libro")
	private Libro libro;
	private String stato;
	private String classificazione;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public String getClassificazione() {
		return classificazione;
	}
	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public Catalogo(Libro libro, String stato) {
		super();
		this.libro = libro;
		this.stato = stato;
	}
	public Catalogo(Libro libro, String stato, String classificazione) {
		super();
		this.libro = libro;
		this.stato = stato;
		this.classificazione = classificazione;
	}
	public Catalogo() {
		super();
	}
	
	
	
}
