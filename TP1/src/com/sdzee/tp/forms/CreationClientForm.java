package com.sdzee.tp.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.dao.ClientDAO;
import com.sdzee.tp.dao.DAOException;

public class CreationClientForm {

	private static final String	CHAMP_NOM		= "nomClient";
	private static final String	CHAMP_PRENOM	= "prenomClient";
	private static final String	CHAMP_ADRESSE	= "adresseClient";
	private static final String	CHAMP_TELEPHONE	= "telephoneClient";
	private static final String	CHAMP_EMAIL		= "emailClient";

	private ClientDAO			clientDAO;
	private Map<String, String>	erreurs			= new HashMap<>();
	private String				resultat;

	public CreationClientForm( ClientDAO clientDAO ) {
		this.clientDAO = clientDAO;
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

	public Client ajouterClient( HttpServletRequest request ) {

		String nom = getValeurChamp( request, CHAMP_NOM );
		String prenom = getValeurChamp( request, CHAMP_PRENOM );
		String adresse = getValeurChamp( request, CHAMP_ADRESSE );
		String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
		String email = getValeurChamp( request, CHAMP_EMAIL );
		Client client = new Client();

		try {
			traiterNomClient( nom, client );

			traiterPrenomClient( prenom, client );

			traiterAdresseClient( adresse, client );

			traiterTelephoneClient( telephone, client );

			traiterEmailClient( email, client );

			if ( erreurs.isEmpty() ) {
				clientDAO.creer( client );
				resultat = "Succ�s de cr�ation du client.";
			} else {
				resultat = "�chec de cr�ation du client.";
			}
		} catch ( DAOException e ) {
			resultat = "�chec de l'inscription : une erreur impr�vue est survenue, merci de r�essayer dans quelques instants.";
			e.printStackTrace();
		}
		return client;

	}

	private void validationNomClient( String nom ) throws FormValidationException {
		if ( nom != null ) {
			if ( nom.trim().length() < 2 ) {
				throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 2 caract�res." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer un nom d'utilisateur." );
		}
	}

	private void validationPrenomClient( String prenom ) throws FormValidationException {
		if ( prenom != null && prenom.trim().length() < 2 ) {
			throw new FormValidationException( "Le pr�nom doit contenir au moins 2 caract�res." );
		}
	}

	private void validationAdresseClient( String adresse ) throws FormValidationException {
		if ( adresse != null ) {
			if ( adresse.trim().length() < 10 ) {
				throw new FormValidationException( "L'adresse de livraison doit contenir au moins 10 caract�res." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer une adresse de livraison." );
		}
	}

	private void validationTelephoneClient( String telephone ) throws FormValidationException {
		if ( telephone != null ) {
			if ( !telephone.matches( "\\d+" ) || telephone.trim().length() < 4 ) {
				throw new FormValidationException( "Un num�ro de t�l�phone doit contenir au moins 4 chiffres." );
			}
		} else {
			throw new FormValidationException( "Merci d'entrer un num�ro de t�l�phone." );
		}
	}

	private void validationEmailClient( String email ) throws FormValidationException {
		if ( email != null ) {
			if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new FormValidationException( "Merci de saisir une adresse mail valide." );
			}
		} else {
			throw new FormValidationException( "Merci de saisir une adresse mail." );
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

	// M�thodes de traitements des champs
	public void traiterNomClient( String nom, Client client ) {
		try {
			validationNomClient( nom );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_NOM, e.getMessage() );
		}
		client.setNom( nom );
	}

	public void traiterPrenomClient( String prenom, Client client ) {
		try {
			validationPrenomClient( prenom );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_PRENOM, e.getMessage() );
		}
		client.setPrenom( prenom );
	}

	public void traiterAdresseClient( String adresse, Client client ) {
		try {
			validationAdresseClient( adresse );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_ADRESSE, e.getMessage() );
		}
		client.setAdresse( adresse );
	}

	public void traiterTelephoneClient( String telephone, Client client ) {
		try {
			validationTelephoneClient( telephone );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_TELEPHONE, e.getMessage() );
		}
		client.setTelephone( telephone );
	}

	public void traiterEmailClient( String email, Client client ) {
		try {
			validationEmailClient( email );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_EMAIL, e.getMessage() );
		}
		client.setEmail( email );

	}
}
