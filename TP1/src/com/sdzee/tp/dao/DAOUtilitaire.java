package com.sdzee.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;

public class DAOUtilitaire {

	// Initialisation de la requête preparée.
	public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql,
			boolean return_generated_keys, Object... objects ) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement( sql,
				return_generated_keys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
		for ( int i = 0; i < objects.length; i++ ) {
			preparedStatement.setObject( i + 1, objects[i] );
		}

		return preparedStatement;
	}

	// Mapping du ResultSet avec le bean Client
	public static Client mapClient( ResultSet resultSet ) throws SQLException {

		Client client = new Client();

		client.setId( resultSet.getLong( "id" ) );
		client.setNom( resultSet.getString( "nom" ) );
		client.setPrenom( resultSet.getString( "prenom" ) );
		client.setAdresse( resultSet.getString( "adresse" ) );
		client.setEmail( resultSet.getString( "email" ) );
		client.setTelephone( resultSet.getString( "telephone" ) );

		return client;
	}

	public static Commande mapCommande( ResultSet resultSet ) throws SQLException {

		Commande commande = new Commande();
		commande.setId( resultSet.getLong( "id" ) );
		commande.setDate( resultSet.getDate( "date" ) );
		commande.setMontant( resultSet.getDouble( "montant" ) );
		commande.setModePaiement( resultSet.getString( "mode_paiement" ) );
		commande.setStatutPaiement( resultSet.getString( "statut_paiement" ) );
		commande.setModeLivraison( resultSet.getString( "mode_livraison" ) );
		commande.setStatutLivraison( resultSet.getString( "statut_livraison" ) );

		return commande;
	}

	// Fermeture des ressources
	public static void fermetureResultSet( ResultSet resultSet ) {
		try {
			if ( resultSet != null ) {
				resultSet.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture du ResultSet: " + e.getMessage() );
		}
	}

	public static void fermetureStatement( Statement statement ) {
		try {
			if ( statement != null ) {
				statement.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture du Statement: " + e.getMessage() );
		}
	}

	public static void fermetureConnection( Connection connection ) {
		try {
			if ( connection != null ) {
				connection.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture de la connexion: " + e.getMessage() );
		}
	}

	public static void fermetureRessources( ResultSet resultSet, Statement statement, Connection connection ) {
		try {
			if ( resultSet != null ) {
				resultSet.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture du ResultSet: " + e.getMessage() );
		}
		try {
			if ( statement != null ) {
				statement.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture du Statement: " + e.getMessage() );
		}
		try {
			if ( connection != null ) {
				connection.close();
			}
		} catch ( SQLException e ) {
			System.out.println( "Échec de la fermeture de la connexion: " + e.getMessage() );
		}
	}
}
