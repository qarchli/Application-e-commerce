package com.sdzee.tp.forms;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.ClientDAO;
import com.sdzee.tp.dao.CommandeDAO;
import com.sdzee.tp.dao.DAOException;;

public class CreationCommandeForm {

	// Commande Fields
	public static final String	FORMAT_DATE				= "dd/MM/yyyy HH:mm:ss";
	private static final String	CHAMP_DATE				= "dateCommande";
	private static final String	CHAMP_MONTANT			= "montantCommande";
	private static final String	CHAMP_MODE_PAIEMENT		= "modePaiementCommande";
	private static final String	CHAMP_STATUT_PAIEMENT	= "statutPaiementCommande";
	private static final String	CHAMP_MODE_LIVRAISON	= "modeLivraisonCommande";
	private static final String	CHAMP_STATUT_LIVRAISON	= "statutLivraisonCommande";
	private static final String	CHOIX_NOUVEAU_CLIENT	= "choixNouveauClient";
	private static final String	CHAMP_LISTE_CLIENTS		= "listeClients";
	private static final String	CHAMP_CLIENT			= "clientForm";
	// Client
	private CreationClientForm	creationClientForm;
	private Date				dtCourante				= new Date( System.currentTimeMillis() % 1000 );
	private ClientDAO			clientDao;
	private CommandeDAO			commandeDao;

	private Map<String, String>	erreurs					= new HashMap<>();
	private String				resultat;

	public CreationCommandeForm( CommandeDAO commandeDao, ClientDAO clientDao ) {
		this.clientDao = clientDao;
		this.commandeDao = commandeDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreur( String key, String value ) {
		this.erreurs.put( key, value );
	}

	public String getResultat() {
		return resultat;
	}

	public CreationClientForm getCreationClientForm() {
		return creationClientForm;
	}

	public Commande ajouterCommande( HttpServletRequest request ) {
		Commande commande = new Commande();

		creationClientForm = new CreationClientForm( clientDao );

		// Choix nouveau client
		String choix = getValeurChamp( request, CHOIX_NOUVEAU_CLIENT );
		String selectedClient = getValeurChamp( request, CHAMP_LISTE_CLIENTS ) == null ? ""
				: getValeurChamp( request, CHAMP_LISTE_CLIENTS );

		if ( choix == "non" ) {
			try {
				traiterAncienClient( Long.parseLong( selectedClient ), commande );
			} catch ( DAOException e ) {
				e.printStackTrace();
			}
		} else if ( choix == "oui" ) {
			try {
				traiterNouveauClient( request, commande );
			} catch ( DAOException e ) {
				e.printStackTrace();
			}
		}
		// Champs commande
		String montant = getValeurChamp( request, CHAMP_MONTANT );
		String modePaiement = getValeurChamp( request, CHAMP_MODE_PAIEMENT );
		String statutPaiement = getValeurChamp( request, CHAMP_STATUT_PAIEMENT );
		String modeLivraison = getValeurChamp( request, CHAMP_MODE_LIVRAISON );
		String statutLivraison = getValeurChamp( request, CHAMP_STATUT_LIVRAISON );

		commande.setDate( dtCourante );
		try {

			traiterMontant( montant, commande );

			traiterModePaiement( modePaiement, commande );

			traiterStatutPaiement( statutPaiement, commande );

			traiterModeLivraison( modeLivraison, commande );

			traiterStatutLivraison( statutLivraison, commande );

			if ( erreurs.isEmpty() ) {
				commandeDao.creer( commande );
				resultat = "Succès de création de la commande.";
			} else {
				resultat = "Échec de création de la commande.";
			}
		} catch ( DAOException e ) {
			resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return commande;
	}

	private void validationMontant( String montant ) throws FormValidationException {
		if ( montant != null ) {
			if ( !montant.matches( "\\d+(\\.\\d+)?" ) || Double.valueOf( montant ) <= 0 ) {
				throw new FormValidationException( "Merci de saisir un montant valide." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer un montant." );
		}
	}

	private void validationModePaiement( String modePaiement ) throws FormValidationException {
		if ( modePaiement != null ) {
			if ( modePaiement.trim().length() < 2 ) {
				throw new FormValidationException( "Le mode de paiement doit contenir au moins 2 caractères." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer un mode de paiement." );
		}
	}

	private void validationStatutPaiement( String statutPaiement ) throws FormValidationException {
		if ( statutPaiement != null ) {
			if ( statutPaiement.trim().length() < 2 ) {
				throw new FormValidationException( "Le statut de paiement doit contenir au moins 2 caractères." );
			}
		}
	}

	private void validationModeLivraison( String modeLivraison ) throws FormValidationException {
		if ( modeLivraison != null ) {
			if ( modeLivraison.trim().length() < 2 ) {
				throw new FormValidationException( "Le mode de livraison doit contenir au moins 2 caractères." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer un mode de livraison." );
		}
	}

	private void validationStatutLivraison( String statutLivraison ) throws FormValidationException {
		if ( statutLivraison != null ) {
			if ( statutLivraison.trim().length() < 2 ) {
				throw new FormValidationException( "Le statut de livraison doit contenir au moins 2 caractères." );
			}
		}
	}

	private void validationChoixNouveauClient( Long choixNouveauClient ) throws FormValidationException {
		if ( choixNouveauClient != null ) {
			if ( choixNouveauClient.toString().startsWith( "Choisissez" ) || choixNouveauClient < 0 ) {
				throw new FormValidationException( "Merci de choisir correctement le client." );
			}
		} else {
			throw new FormValidationException( "Merci de sélectionner un client existant. Sinon, créez-vous un." );
		}
	}

	private String getValeurChamp( HttpServletRequest request, String nomChamp ) {
		String valeur = request.getParameter( nomChamp );
		if ( valeur == null || valeur.trim().length() == 0 ) {
			return null;
		} else {
			return valeur.trim();
		}
	}

	public void traiterMontant( String montant, Commande commande ) {
		try {
			validationMontant( montant );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_MONTANT, e.getMessage() );
		}
		commande.setMontant( montant == null ? 0.0 : Double.valueOf( montant ) );
	}

	public void traiterModePaiement( String modePaiement, Commande commande ) {
		try {
			validationModePaiement( modePaiement );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
		}
		commande.setModePaiement( modePaiement );
	}

	public void traiterStatutPaiement( String statutPaiement, Commande commande ) {
		try {
			validationStatutPaiement( statutPaiement );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
		}
		commande.setStatutPaiement( statutPaiement );
	}

	public void traiterModeLivraison( String modeLivraison, Commande commande ) {
		try {
			validationModeLivraison( modeLivraison );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
		}
		commande.setModeLivraison( modeLivraison );
	}

	public void traiterStatutLivraison( String statutLivraison, Commande commande ) {
		try {
			validationStatutLivraison( statutLivraison );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
		}
		commande.setStatutLivraison( statutLivraison );
	}

	public void traiterNouveauClient( HttpServletRequest request, Commande commande ) {
		Client client = new Client();
		if ( commande == null ) {
			commande = new Commande();
		}
		client = creationClientForm.ajouterClient( request );
		clientDao.creer( client );
		commande.setClient( client );
	}

	public void traiterAncienClient( Long id, Commande commande ) {
		Client client = new Client();
		if ( commande == null ) {
			commande = new Commande();
		}
		try {
			client = clientDao.trouver( id );
		} catch ( DAOException e ) {
			setErreur( CHAMP_CLIENT, e.getMessage() );
		}
		System.out.println( client.toString() );
		commande.setClient( client );
	}

}
