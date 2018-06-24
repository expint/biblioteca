package it.online.biblioteca.model;

public class Messaggio {
	public static final String ERROR = "danger";
	public static final String WARNING = "warning";
	public static final String SUCCESS = "success";
	
	private String tipo;
	private String titolo;
	private String testo;
	private boolean visualizzato = false;
	
	public boolean isVisualizzato() {
		return visualizzato;
	}

	public void setVisualizzato(boolean visualizzato) {
		this.visualizzato = visualizzato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Messaggio() {
		super();
	}

	public Messaggio(String tipo, String titolo, String testo) {
		super();
		this.tipo = tipo;
		this.titolo = titolo;
		this.testo = testo;
	}

	public Messaggio(String tipo, String testo) {
		super();
		this.tipo = tipo;
		this.testo = testo;
	}
	
	public void annullaMessaggio() {
		this.tipo = null;
		this.titolo = null;
		this.testo = null;
		this.visualizzato = false;
	}
	
	public void verificaMessaggio() {
		if(this.testo!=null) {
			if (!this.isVisualizzato()) {
				this.setVisualizzato(true);
			}
			else {
				this.annullaMessaggio();
			}
		}
	}
	
	public void sostituisciMessaggio(Messaggio messaggio) {
		this.tipo = messaggio.getTipo();
		this.testo = messaggio.getTesto();
		this.visualizzato = false;
	}
	
	
	
	
}
