package it.online.biblioteca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="libri")
public class Libro {
	public static final String GEN_NAR_ITA = "Narrativa italiana";
	public static final String GEN_NAR_STR = "Narrativa straniera";
	public static final String GEN_THR = "Thriller";
	public static final String GEN_GIA = "Gialli";
	public static final String GEN_HOR = "Horror";
	public static final String GEN_PSI = "Psicologia";
	public static final String GEN_FIC= "Fantascienza";
	public static final String GEN_ECD= "Economia e diritto";
	public static final String GEN_MIN= "Bambini e ragazzi";
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	private String titolo;
	private String autore;
	private String genere;
	private String isbn;
	private String editore;
	@Column(name="anno")
	private Integer annoPubblicazione;
	
	public Libro() {
		super();
	}
	
	public Libro(String titolo, String autore, String isbn) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.isbn = isbn;
	}

	public Libro(String titolo, String autore, Integer annoPubblicazione) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.annoPubblicazione = annoPubblicazione;
	}

	public Libro(String titolo, String autore) {
		super();
		this.titolo = titolo;
		this.autore = autore;
	}
	public Libro(String titolo) {
		super();
		this.titolo = titolo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getEditore() {
		return editore;
	}
	public void setEditore(String editore) {
		this.editore = editore;
	}
	public Integer getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	public void setAnnoPubblicazione(Integer annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public Libro(String titolo, String autore, String genere, String isbn, String editore, Integer annoPubblicazione) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.genere = genere;
		this.isbn = isbn;
		this.editore = editore;
		this.annoPubblicazione = annoPubblicazione;
	}
	
	
	
		
}
