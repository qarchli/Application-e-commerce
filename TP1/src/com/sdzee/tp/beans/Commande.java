package com.sdzee.tp.beans;

import java.sql.Date;

public class Commande {
	private Long	id;
	private Client	client;
	private Date	date;
	private Double	montant;
	private String	modePaiement;
	private String	statutPaiement;
	private String	modeLivraison;
	private String	statutLivraison;

	public String toString() {
		return "ID: " + id + "\nClient: " + client.getId() + "\nDate: " + date + "\nMontant: " + montant
				+ "\nMode Paiement: " + modePaiement + "\nStatutPaiement: " + statutPaiement + "\nMode livraison: "
				+ modeLivraison + "\nStatut Livraison: " + statutLivraison;
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant( Double montant ) {
		this.montant = montant;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement( String modePaiement ) {
		this.modePaiement = modePaiement;
	}

	public String getStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement( String statutPaiement ) {
		this.statutPaiement = statutPaiement;
	}

	public String getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison( String modeLivraison ) {
		this.modeLivraison = modeLivraison;
	}

	public String getStatutLivraison() {
		return statutLivraison;
	}

	public void setStatutLivraison( String statutLivraison ) {
		this.statutLivraison = statutLivraison;
	}

	public Client getClient() {
		return client;
	}

	public void setClient( Client client ) {
		this.client = client;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

}
